# 🔍 Guía de Depuración - Login

## Problema: No se puede iniciar sesión

### 📝 Credenciales de Prueba
- **Usuario**: MONSTER
- **Contraseña**: MONSTER9

### ✅ Pasos para verificar la conexión

#### 1️⃣ Verificar que el servidor RESTful está ejecutándose

Desde tu navegador web o Postman, accede a:
```
http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/corebancario/ping
```

Deberías ver una respuesta JSON como:
```json
{"msg":"ok"}
```

Si no lo ves, el servidor no está ejecutándose.

#### 2️⃣ Verificar la URL en la aplicación

Abre el archivo:
```
app/src/main/java/ec/edu/monster/service/RestApiService.kt
```

Verifica que la URL esté configurada correctamente según tu caso:

**Si usas EMULADOR Android:**
```kotlin
private val baseUrl = "http://10.0.2.2:8080/WSEurekaBank_Restfull_Java_G4/resources/"
```

**Si usas DISPOSITIVO FÍSICO en la misma red:**
```kotlin
private val baseUrl = "http://TU_IP:8080/WSEurekaBank_Restfull_Java_G4/resources/"
```
Para obtener tu IP:
- Windows: Ejecuta `ipconfig` en CMD
- Busca "Adaptador de LAN inalámbrica Wi-Fi"
- Anota la "Dirección IPv4" (ej: 192.168.1.100)

#### 3️⃣ Ver los logs de depuración

La aplicación ahora incluye logs detallados. Para verlos:

1. Conecta tu dispositivo/emulador
2. En Android Studio, abre la ventana **Logcat**
3. Filtra por "System.out" o busca estos emojis:
   - 🌐 Conectando a...
   - 👤 Usuario...
   - 📤 Request enviado...
   - 📥 Response recibido...
   - ✅ Resultado...
   - ❌ Error...

#### 4️⃣ Probar la conexión manualmente

Puedes probar el servicio con Postman:

**URL**: `http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/corebancario/login?usuario=MONSTER&password=MONSTER9`

**Método**: POST

**Respuesta esperada**:
```json
{"resultado":"Exitoso"}
```

### 🔧 Soluciones Comunes

#### Error: "Connection refused" o "Failed to connect"
- ✓ Verifica que el servidor esté ejecutándose
- ✓ Si usas emulador, usa `10.0.2.2` en vez de `localhost`
- ✓ Si usas dispositivo físico, verifica que estés en la misma red WiFi
- ✓ Desactiva el firewall temporalmente para probar

#### Error: "Credenciales incorrectas"
- ✓ Verifica que las credenciales sean exactamente: MONSTER / MONSTER9
- ✓ Verifica que el servidor tenga estos usuarios registrados
- ✓ Revisa los logs del servidor para ver qué está recibiendo

#### El servidor responde pero dice "Denegado"
- ✓ Verifica que el usuario MONSTER exista en la base de datos
- ✓ Verifica que la contraseña esté correctamente hasheada en la BD
- ✓ Revisa los logs del servidor RESTful

### 📊 Verificar respuesta del servidor

En los logs de Logcat, busca la línea que dice:
```
Response body: ...
```

Esto te mostrará exactamente qué está respondiendo el servidor.

### 🎯 Cambios realizados en el código

He agregado:
1. **Logs detallados** en `RestApiService.kt` para ver cada paso
2. **Manejo de errores mejorado** con mensajes descriptivos
3. **Información de depuración** en el LoginScreen
4. **Retrofit con Gson** para consumir API RESTful JSON

### 📞 Si nada funciona

1. Copia los logs completos de Logcat
2. Verifica que el servidor RESTful esté respondiendo correctamente
3. Prueba las credenciales directamente en el servidor con Postman
4. Verifica que `usesCleartextTraffic="true"` esté en AndroidManifest.xml (ya está configurado)

---

## 🎨 Mejoras visuales implementadas

✅ **Header en contenedor blanco** con texto morado (mezcla rojo-azul)
✅ **"Ingreso al sistema" centrado**
✅ **Texto de entrada más visible** (negro en vez de gris claro)
✅ **Logs de depuración** para diagnosticar problemas de conexión
✅ **API RESTful con JSON** en lugar de SOAP
