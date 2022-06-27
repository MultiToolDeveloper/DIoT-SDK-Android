# DIoT Android SDK

**Welcome to the DIoT Android SDK Application page!**

DIoT is a platform for different IoT devices provided by Daatrics LTD company.
The SDK is released for iOS and Android systems and and you are free to use it to implement a connectivity between your phone and device via BLE. 

SDK project also contains demo apps with a whole work flow implementation.

## Initialization

The initialization process contains several steps:
1) Add SDK dependency to your app **build.gradle** module:

       dependencies {
           ...
           implementation 'com.github.MultiToolDeveloper:DIoT-SDK-Android:1.0.1'  
           ...
       }

2) Add jitpack.io repo to your root **settings.gradle** module:

       repositories {
           ...
           maven { url 'https://jitpack.io' }
           ...
       }

3) Add Bluetooth and Location permissions to **AndroidManifest.xml**

       <manifest
           ...
           <uses-permission android:name="android.permission.BLUETOOTH" />
           <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
           <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
           <uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
           <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
           <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
           ...
           <application
               ...
           </application>
           ...
       </manifest>

4) Initialize SDK in Application singleton:

       class App : Application() {
           override fun onCreate() {
               super.onCreate()
               //do init the iot sdk
               DIoT.initialize(this)
           }
       }

## Scanning

The main SDK Bluetooth module is DIoTBluetoothManager. This class is used as an additional level after the system classes BluetoothManager and BluetoothAdapter. It is used to: check the status of the BT system adapter, scann BLE devices, forward status events to service subscribers.

		interface DIoTBluetoothManagerProtocol {
			val context: Context
			val coroutineContext: CoroutineContext
			fun subscribe(subscriber: DIoTBluetoothManagerDelegate, subscriptionType: DIoTBluetoothManagerSubscriptionType)
			fun unsubscribe(subscriber: DIoTBluetoothManagerDelegate, subscriptionType: DIoTBluetoothManagerSubscriptionType)
			fun fetchBluetoothPowerState()
			fun startScan(service: UUID?, name: String?)
			fun stopScan()
		}
		
To get the scan result, you need to subscribe to DIoTBluetoothManager events, for example:

		DIoT.bluetoothManager?.subscribe(this, DIoTBluetoothManagerSubscriptionType.scan)
		DIoT.bluetoothManager?.startScan(null, null)
		DIoT.bluetoothManager?.stopScan()

You will receive a callback to your delegate implementation:

		interface DIoTBluetoothManagerScanningDelegate: DIoTBluetoothManagerDelegate {
			fun didDiscoverDevice(manager: DIoTBluetoothManagerProtocol, device: DIoTBluetoothDevice, rssi: Int)
			fun didReceiveScanningError(manager: DIoTBluetoothManagerProtocol, error: DIoTBluetoothManagerScanningError)
		}

To get information about the status of the BT hardware module, you need to subscribe to DIoTBluetoothManager events, for example:

		DIoT.bluetoothManager?.subscribe(this, DIoTBluetoothManagerSubscriptionType.state)
		DIoT.bluetoothManager?.fetchBluetoothPowerState()

After the request next delegate will be triggered:

		interface DIoTBluetoothManagerStateDelegate: DIoTBluetoothManagerDelegate {
			fun bluetoothManagerEnabledBluetooth(manager: DIoTBluetoothManagerProtocol)
			fun bluetoothManagerDisabledBluetooth(manager: DIoTBluetoothManagerProtocol)
			fun bluetoothManagerNotAllowedBluetooth(manager: DIoTBluetoothManagerProtocol)
			fun bluetoothManagerNoBLESupport(manager: DIoTBluetoothManagerProtocol)
		}

## Device

As a result of the scan, one or more DIoT devices will be returned, this class contains connection functions, a list of services and signal strength determination, as well as information about the device (name, id, etc.):

		interface DIoTBluetoothDeviceProtocol {
			var name: String
			var deviceId: DeviceId
			var address: String

			var connectionService: DIoTBluetoothDeviceConnectionServiceProtocol
			var deviceInformationService: GeneralDeviceInformationBluetoothServiceProtocol?
			var batteryService: GeneralBatteryBluetoothServiceProtocol?
			var commandInterfaceService: DIoTCommandInterfaceBluetoothServiceProtocol?
			var dataInterfaceService: (DIoTDataInterfaceBluetoothServiceProtocol)?
			var deviceIdentifierService: DIoTDeviceIdBluetoothServiceProtocol?
			var debugService: DIoTDebugBluetoothServiceProtocol?
		}

Device structure contains services and functions with data structures is used to communication with IoT device via BLE connection.

- connectionService is used to connect and disconnect to current device
- deviceInformationService is used to get device's version information
- batteryService is used to get device's battery information
- commandInterfaceService is used to get current device features, and setup connection between features and data channels (for example: 1 -  get list of the current device features; 2 - get one of those features and connect it to the data channel; 3 - set up the data rate on the target data channel). The data rate sets up like integer number in ms (the current channel update delay), default data rate is 0 or auto update.
- dataInterfaceService is used to get data from the connected features (each device content up to 9 data channels with indexes: 1...9)
- deviceIdentifierService - is used to receive device UUID
- debugService - is used to communicate with internal CLI

## Connection

To connect to the device, you must use the connect method in the ConnectionService service of the DIoTBluetoothDevice class:

		var device: DIoTBluetoothDevice? = ... //result of a scan
		device?.connectionService?.subscribe(this)
		device?.connectionService?.connect()

You have to use the connectionService.subscribe() function and implement a delegate interface inside you class to receive connection state callbacks:

		interface DIoTBluetoothDeviceConnectionServiceDelegate {
			fun didConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
			fun didDisconnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
			fun didFailToConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
			fun didReceiveError(service: DIoTBluetoothDeviceConnectionServiceProtocol, error: DIoTBluetoothDeviceConnectionError)
			fun didReceiveRSSI(service: DIoTBluetoothDeviceConnectionServiceProtocol, rssi: Int)
		}

## Data exchange
The data exchange is carried out through services designated in the DIoTBluetoothDevice class.

		interface DIoTBluetoothDeviceProtocol {
			var name: String
			var deviceId: DeviceId
			var address: String

			var connectionService: DIoTBluetoothDeviceConnectionServiceProtocol
			var deviceInformationService: GeneralDeviceInformationBluetoothServiceProtocol?
			var batteryService: GeneralBatteryBluetoothServiceProtocol?
			var commandInterfaceService: DIoTCommandInterfaceBluetoothServiceProtocol?
			var dataInterfaceService: (DIoTDataInterfaceBluetoothServiceProtocol)?
			var deviceIdentifierService: DIoTDeviceIdBluetoothServiceProtocol?
			var debugService: DIoTDebugBluetoothServiceProtocol?
		}
Each service contains methods related to the data involved in this service, as well as methods and corresponding class delegates that the signatories must correspond to. You can subscribe  and unsubscribe on events with:

		fun subscribe(subscriber:)
		fun unsubscribe(subscriber:)

## Data request example

Let's try to get data from the DIoT Command Interface service.

1) Subscribe to receive data from the DIoT Command Interface service

   		device?.commandInterfaceService?.subscribe(this)
2) Implement the DIoTCommandInterfaceBluetoothServiceDelegate methods:

		override fun didReceiveCommandFeatures(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			dataFeatures: ArrayList<DIoTFeatureData>
		) {
		}

		override fun didReceiveCommandChannels(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			dataChannels: ArrayList<DIoTChannelData>
		) {
		}

		override fun didReceiveCommandRate(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			dataRates: ArrayList<DIoTRateData>
		) {
		}

		override fun didReceiveError(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			error: DIoTCommandInterfaceBluetoothServiceError
		) {
		}

		override fun didWriteCommandFeatures(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
		}

		override fun didWriteCommandChannels(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
		}

		override fun didWriteCommandRate(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
		}

		override fun subscriptionFeaturesStatusChange(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			enabled: Boolean
		) {
		}

		override fun subscriptionChannelsStatusChange(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			enabled: Boolean

		) {
		}

		override fun subscriptionRatesStatusChange(
			service: DIoTCommandInterfaceBluetoothServiceProtocol,
			enabled: Boolean
		) {
		}

3) Request data

All possible data requests are showed in the class interface source file:

		interface DIoTCommandInterfaceBluetoothServiceProtocol {
			fun fetchFeatures()
			fun fetchChannels()
			fun fetchRates()
			fun fetchFeature(code: DIoTFeatureCode)
			fun fetchChannel(channel: Int)
			fun fetchRate(channel: Int)
			fun notifyFeatures(enable: Boolean)
			fun notifyChannels(enable: Boolean)
			fun notifyRates(enable: Boolean)

			fun setFeature(feature: DIoTFeatureData)
			fun cleanFeature(code: DIoTFeatureCode)
			fun cleanFeatures()
			fun setChannel(channel: Int, code: DIoTFeatureCode)
			fun cleanChannel(channel: Int)
			fun cleanChannels()
			fun setRate(channel: Int, rate: Int)
			fun cleanRate(channel: Int)
			fun cleanRates()

			fun subscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate)
			fun unsubscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate)
		}

For example:

		device?.commandInterfaceService?.fetchFeature(featureData.getFeatureCode())
		device?.commandInterfaceService?.cleanFeature(featureData.getFeatureCode())
		device?.commandInterfaceService?.setChannel(number, featureData.getFeatureCode())
		device?.commandInterfaceService?.fetchFeatures()
		...

A data response will be received in the delegate which was set up by subscribe function previously.

4) Unsubscribe from data events

   		device?.commandInterfaceService?.unsubscribe(this)
