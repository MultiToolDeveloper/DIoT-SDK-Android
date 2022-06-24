package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.ParcelUuid
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDeviceProtocol
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.enums.DeviceIdError
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.parsers.DeviceIdParser
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.toHexString
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces.LoggerServiceProtocol
import java.util.*

typealias OperationBlock = () -> Unit

class DIoTBluetoothManager(
    override val context: Context,
    override val bleDispatcher: DIoTAndroidBleDispatcher,
):
    DIoTBluetoothManagerProtocol
{
    var deviceIdParser = DeviceIdParser()
    var connectedPeripherals: ArrayList<BluetoothGatt> = ArrayList()
    var operations: ArrayList<OperationBlock> = ArrayList()
    var subscribers: HashMap<DIoTBluetoothManagerSubscriptionType, DIoTHashTable> = HashMap()
    var logger: LoggerServiceProtocol? = null

    private val bluetoothAdapter: BluetoothAdapter?
    private val bleScanner: BluetoothLeScanner?
    private val bluetoothManager: BluetoothManager?

    init {
        for (type in DIoTBluetoothManagerSubscriptionType.values())
            subscribers.put(type, DIoTHashTable())

        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothAdapter = bluetoothManager?.getAdapter()
        bleScanner = bluetoothAdapter?.getBluetoothLeScanner()
    }

    //BluetoothGattCallback
    private val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        subscriber.didUpdateValueFor(gatt, characteristic, null)
                    }
                }
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            it.didUpdateValueFor(gatt, characteristic, null)
                        } else {
                            it.didUpdateValueFor(
                                gatt,
                                characteristic,
                                Error("Error onCharacteristicRead, received: $status")
                            )
                        }
                    }
                }
            }
        }

        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        bleDispatcher.setExecutorEnabled(true)

                        //sometimes STATE_CONNECTED repeats several times we need to use workaround
                        for (peripheral in connectedPeripherals){
                            if (peripheral.device == gatt.device)
                                return
                        }

                        connectedPeripherals.add(gatt)
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        bleDispatcher.setExecutorEnabled(false)
                        connectedPeripherals.remove(gatt)
                    }
                    BluetoothProfile.STATE_CONNECTING -> {}
                    BluetoothProfile.STATE_DISCONNECTING -> {}
                    else -> {}
                }
            } else {
                connectedPeripherals.remove(gatt)
            }

            subscribers[DIoTBluetoothManagerSubscriptionType.connection]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerConnectionDelegate)?.let {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            when (newState) {
                                BluetoothProfile.STATE_CONNECTED -> {
                                    it.didConnectTo(this@DIoTBluetoothManager, gatt)
                                }
                                BluetoothProfile.STATE_DISCONNECTED -> {
                                    it.didDisconnectFrom(this@DIoTBluetoothManager, gatt)
                                }
                                BluetoothProfile.STATE_CONNECTING -> {}
                                BluetoothProfile.STATE_DISCONNECTING -> {}
                                else -> {}
                            }
                        } else {
                            it.didFailToConnect(this@DIoTBluetoothManager, gatt, Error("Error onConnectionStateChange, received: $status"))
                        }
                    }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        when (status) {
                            BluetoothGatt.GATT_SUCCESS -> {
                                it.didDiscoverServices(gatt, null)
                                for (service in gatt.services){
                                    it.didDiscoverCharacteristicsFor(gatt, service, null)
                                }
                            }
                            BluetoothGatt.GATT_READ_NOT_PERMITTED,
                            BluetoothGatt.GATT_WRITE_NOT_PERMITTED,
                            BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION,
                            BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED,
                            BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION,
                            BluetoothGatt.GATT_INVALID_OFFSET,
                            BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH,
                            BluetoothGatt.GATT_CONNECTION_CONGESTED,
                            BluetoothGatt.GATT_FAILURE -> {
                                it.didDiscoverServices(gatt, Error("Error onServicesDiscovered, received: $status"))
                            }
                            else -> {
                                it.didDiscoverServices(gatt, Error("Error onServicesDiscovered, received: $status"))
                            }
                        }
                    }
                }
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic, status: Int
        ) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            it.didWriteValueFor(gatt, characteristic, null)
                        } else {
                            it.didWriteValueFor(
                                gatt,
                                characteristic,
                                Error("Error onCharacteristicWrite, received: $status")
                            )
                        }
                    }
                }
            }
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor, status: Int
        ) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            if (Arrays.equals(descriptor.value, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) {
                                it.didUpdateNotificationStateFor(gatt, descriptor.characteristic, true,null)
                            } else {
                                it.didUpdateNotificationStateFor(gatt, descriptor.characteristic, false,null)
                            }
                        } else {
                            it.didUpdateNotificationStateFor(
                                gatt,
                                descriptor.characteristic,
                                false,
                                Error("Error onDescriptorWrite, received: $status")
                            )
                        }
                    }
                }
            }
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
            subscribers[DIoTBluetoothManagerSubscriptionType.data]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerDataUpdateDelegate)?.let {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            it.didReadRSSI(gatt, rssi, null)
                        } else {
                            it.didReadRSSI(gatt, rssi, Error("Error onReadRemoteRssi, received: $status"))
                        }
                    }
                }
            }
        }
    }

    //DIoTBluetoothStateManagerProtocol
    override fun fetchBluetoothPowerState() {
        subscribers[DIoTBluetoothManagerSubscriptionType.state]?.let {
            for (subscriber in it.values) {
                (subscriber as? DIoTBluetoothManagerStateDelegate)?.let {
                    //bluetooth check
                    val isBluetoothLeSupported = context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
                    if (bluetoothAdapter == null ) {
                        it.bluetoothManagerNotAllowedBluetooth(this)
                    } else if (!bluetoothAdapter.isEnabled()) {
                        it.bluetoothManagerDisabledBluetooth(this)
                    } else if (!isBluetoothLeSupported){
                        it.bluetoothManagerNoBLESupport(this)
                    } else {
                        it.bluetoothManagerEnabledBluetooth(this)
                    }
                }
            }
        }
    }

    //DIoTBluetoothScanningManagerProtocol
    @SuppressLint("MissingPermission")
    override fun startScan(service: UUID?,  name: String?) {

        val filterBuilder = ScanFilter.Builder()
        name?.let { filterBuilder.setDeviceName(it) }
        service?.let { filterBuilder.setServiceUuid(ParcelUuid(service)) }

        val filters: ArrayList<ScanFilter> = ArrayList()
        filters.add(filterBuilder.build())

        val settingsBuilder = ScanSettings.Builder()
        settingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)

        waitForCertainCentralStateIfNeeded {
            logger?.info("[Bluetooth Manager] start scanning for peripherals with service $service and name $name",null, this.javaClass.name)
            bleScanner?.startScan(filters, settingsBuilder.build(), bleScanCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override fun stopScan() {
        waitForCertainCentralStateIfNeeded {
            logger?.info("[Bluetooth Manager] stop scanning for peripherals",null, this.javaClass.name)
            bleScanner?.stopScan(bleScanCallback)
        }
    }

    //android bluetooth le callback
    val bleScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {

            val rssi = result.rssi
            val deviceName = result.device.name
            val manufactureSpecificData = result.scanRecord?.getManufacturerSpecificData(1271) //0x0059//0x0030
                ?: return

            try {
                val deviceId = deviceIdParser.parseDeviceId(manufactureSpecificData.toHexString())

                //create bluetooth device
                val device = GeneralBluetoothDevice(
                    context,
                    bleDispatcher,
                    deviceId,
                    deviceName,
                    result.device,
                    this@DIoTBluetoothManager
                )

                //init diot bluetooth device from general device
                val diotDevice = DIoTBluetoothDevice(device)

                subscribers[DIoTBluetoothManagerSubscriptionType.scan]?.let {
                    for (subscriber in it.values) {
                        (subscriber as? DIoTBluetoothManagerScanningDelegate)?.let {
                            it.didDiscoverDevice(this@DIoTBluetoothManager, diotDevice, rssi)
                        }
                    }
                }
            } catch (e: DeviceIdError){
                subscribers[DIoTBluetoothManagerSubscriptionType.scan]?.let {
                    for (subscriber in it.values) {
                        (subscriber as? DIoTBluetoothManagerScanningDelegate)?.let {
                            val error = DIoTBluetoothManagerScanningError.cannotParseAdvertisementId(e)
                            it.didReceiveScanningError(this@DIoTBluetoothManager, error)
                        }
                    }
                }
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            //Todo: not using now
        }

        override fun onScanFailed(errorCode: Int) {
            subscribers[DIoTBluetoothManagerSubscriptionType.scan]?.let {
                for (subscriber in it.values) {
                    (subscriber as? DIoTBluetoothManagerScanningDelegate)?.let {
                        val error = DIoTBluetoothManagerScanningError.anotherScanError(errorCode)
                        it.didReceiveScanningError(this@DIoTBluetoothManager, error)
                    }
                }
            }
        }
    }

    //DIoTBluetoothConnectionManagerProtocol
    @SuppressLint("MissingPermission")
    override fun connect(generalDevice: GeneralBluetoothDeviceProtocol) {
        waitForCertainCentralStateIfNeeded {
            logger?.info("[Bluetooth Manager] start connecting to deivce $generalDevice", null, this.javaClass.name)
            generalDevice.device.connectGatt(context, false, gattCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override fun disconnect(generalDevice: GeneralBluetoothDeviceProtocol) {
        waitForCertainCentralStateIfNeeded {
            logger?.info("[Bluetooth Manager] start disconnecting from gatt $generalDevice", null, this.javaClass.name)
            generalDevice.gatt?.disconnect()
        }
    }

    //DIoTBluetoothManagerProtocol
    override fun subscribe(
        subscriber: DIoTBluetoothManagerDelegate,
        subscriptionType: DIoTBluetoothManagerSubscriptionType
    ) {
        if (!(subscribers[subscriptionType]?.contains(subscriber) ?: true))
            subscribers[subscriptionType]?.add(subscriber)
    }

    override fun unsubscribe(
        subscriber: DIoTBluetoothManagerDelegate,
        subscriptionType: DIoTBluetoothManagerSubscriptionType
    ) {
        if ((subscribers[subscriptionType]?.contains(subscriber) ?: false))
            subscribers[subscriptionType]?.remove(subscriber)
    }

    @SuppressLint("MissingPermission")
    fun waitForCertainCentralStateIfNeeded(operationBlock: OperationBlock){
        operations.add(operationBlock)
        bluetoothAdapter?.let {
            if (it.isEnabled){
                for (operation in operations) {
                    operation()
                }
                operations.clear()
            }
        }
    }

}

