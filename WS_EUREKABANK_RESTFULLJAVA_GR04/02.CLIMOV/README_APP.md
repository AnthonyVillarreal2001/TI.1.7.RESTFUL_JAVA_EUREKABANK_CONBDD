# EurekaBank Mobile - Cliente RESTful Android

Aplicación móvil Android que se conecta al servicio RESTful de EurekaBank para realizar operaciones bancarias.

## 🏦 Funcionalidades

1. **Login**: Autenticación de usuarios
2. **Consultar Movimientos**: Ver historial de transacciones de una cuenta
3. **Depósito**: Realizar depósitos a una cuenta
4. **Retiro**: Realizar retiros de una cuenta
5. **Transferencia**: Transferir dinero entre cuentas

## ⚙️ Configuración Importante

### 1. Configurar la URL del Servidor RESTful

Antes de ejecutar la aplicación, **DEBES** actualizar la URL del servidor RESTful en el archivo:

```
app/src/main/java/ec/edu/monster/service/RestApiService.kt
```

Busca la línea:
```kotlin
private val baseUrl = "http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/"
```

Y cámbiala por la IP/HOST de tu servidor. Ejemplos:

```kotlin
// Si usas emulador Android y el servidor está en tu PC:
private val baseUrl = "http://10.0.2.2:8080/WSEurekaBank_Restfull_Java_G4/resources/"

// Si usas dispositivo físico en la misma red:
private val baseUrl = "http://192.168.1.100:8080/WSEurekaBank_Restfull_Java_G4/resources/"

// Si usas servidor en la nube:
private val baseUrl = "http://tu-servidor.com:8080/WSEurekaBank_Restfull_Java_G4/resources/"
```

### 2. Verificar que el servidor esté ejecutándose

Asegúrate de que el servidor RESTful esté corriendo y accesible desde el dispositivo/emulador.

### 3. Permisos de Internet

La aplicación ya tiene configurados los permisos necesarios en `AndroidManifest.xml`:
- `INTERNET`
- `ACCESS_NETWORK_STATE`
- `usesCleartextTraffic="true"` (para conexiones HTTP)

## 🎨 Diseño

La aplicación sigue el diseño de EurekaBank | Liga de Quito con:
- Colores: Rojo primario, azul oscuro, degradados
- Logo de Liga de Quito
- Imagen de perfil Monster
- Interfaz moderna con Material Design 3
- Soporte para orientación horizontal en la tabla de movimientos

## 📱 Requisitos

- Android SDK 24 o superior
- Kotlin 1.9+
- Jetpack Compose
- Conexión a Internet

## 🚀 Cómo ejecutar

1. Abre el proyecto en Android Studio
2. Configura la URL del servidor en `RestApiService.kt`
3. Sincroniza Gradle
4. Ejecuta en emulador o dispositivo físico

## 🔐 Credenciales de Prueba

Usuario: `MONSTER`
Password: `MONSTER9`

## 📋 Estructura del Proyecto

```
app/src/main/java/ec/edu/monster/
├── MainActivity.kt                 # Actividad principal
├── components/                     # Componentes reutilizables
│   └── Dialogos.kt                # Diálogos de éxito y error
├── model/                         # Modelos de datos
│   ├── Movimiento.kt
│   ├── OperacionCuentaResponse.kt
│   ├── LoginResponse.kt
│   └── Usuario.kt
├── navigation/                    # Navegación
│   └── NavigationGraph.kt
├── screens/                       # Pantallas
│   ├── LoginScreen.kt
│   ├── MenuScreen.kt
│   ├── MovimientosScreen.kt
│   ├── DepositoScreen.kt
│   ├── RetiroScreen.kt
│   └── TransferenciaScreen.kt
├── service/                       # Servicios
│   ├── RestApiService.kt         # Cliente RESTful
│   └── EurekaBankApi.kt          # Interface Retrofit
└── ui/theme/                     # Tema y colores
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación
- **Jetpack Compose**: Framework de UI declarativa
- **Navigation Compose**: Navegación entre pantallas
- **Retrofit**: Cliente HTTP RESTful
- **Gson**: Serialización/Deserialización JSON
- **OkHttp**: Cliente HTTP y logging
- **Coil**: Carga de imágenes
- **Material Design 3**: Diseño de interfaz
- **Coroutines**: Programación asíncrona

## 📝 Notas

- La aplicación está configurada para `usesCleartextTraffic="true"` para permitir conexiones HTTP. En producción, usa HTTPS.
- La tabla de movimientos se adapta automáticamente a la orientación del dispositivo.
- Los diálogos de error y éxito siguen el diseño mostrado en las imágenes de referencia.

## 👨‍💻 Autor

Grupo 04 - Arquitectura BDD
