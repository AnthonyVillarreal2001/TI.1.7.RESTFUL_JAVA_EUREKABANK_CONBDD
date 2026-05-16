# 📱 GUÍA DE USO - EurekaBank Mobile App

## 🎯 ¿Cómo Funciona la Aplicación?

### 1️⃣ **PANTALLA DE LOGIN**

**¿Qué hago aquí?**
- Ingresas tu usuario y contraseña
- Credenciales correctas:
  - **Usuario**: `MONSTER`
  - **Contraseña**: `MONSTER9`

**¿Qué pasa si las credenciales son incorrectas?**
- Verás un mensaje: "Credenciales incorrectas. Usuario: MONSTER"
- Verifica que hayas escrito bien la contraseña (sin espacios extra)

**¿Qué pasa cuando ingreso correctamente?**
- Te llevará al MENÚ PRINCIPAL con 4 opciones

---

### 2️⃣ **MENÚ PRINCIPAL**

**¿Qué veo aquí?**
- Arriba: Tu foto de perfil (Monster) y nombre
- 4 tarjetas con opciones:
  1. **Consultar Movimientos** (rojo-azul)
  2. **Depósito** (morado-azul)
  3. **Retiro** (morado-azul)
  4. **Transferencia** (morado-azul)
- Botón "Cerrar Sesión" arriba a la derecha

**¿Qué hace cada opción?**
- Toca una tarjeta para ir a esa funcionalidad

---

### 3️⃣ **CONSULTAR MOVIMIENTOS**

**¿Para qué sirve?**
- Ver el historial de transacciones de una cuenta

**¿Cómo la uso?**
1. Ingresa un número de cuenta (ejemplo: `00100001`)
2. Presiona "VER MOVIMIENTOS"

**¿Qué pasa después?**

**SI LA CUENTA TIENE MOVIMIENTOS:**
- Verás una tabla con:
  - **NRO**: Número del movimiento
  - **FECHA**: Cuándo se hizo
  - **TIPO**: DEPÓSITO, RETIRO, TRANSFERENCIA, INTERÉS
  - **ACCIÓN**: INGRESO o SALIDA
  - **IMPORTE**: Cantidad de dinero

**Colores en TIPO:**
- 🟢 Verde = DEPOSITO
- 🔴 Rojo = RETIRO
- 🟣 Rosa = TRANSFERENCIA
- 🔵 Azul = INTERES

**SI LA CUENTA NO TIENE MOVIMIENTOS:**
- Verás un diálogo de error:
  - ❌ Título: "Error en la Operación"
  - 📝 Mensaje: "No hay movimientos registrados para esta cuenta."
  - ⚠️ Advertencia: "Verifique que el número de cuenta sea correcto..."

**SI GIRAS EL TELÉFONO (HORIZONTAL):**
- La tabla se ajusta automáticamente para verse mejor en horizontal

---

### 4️⃣ **DEPÓSITO**

**¿Para qué sirve?**
- Agregar dinero a una cuenta

**¿Cómo la uso?**
1. Ingresa el número de cuenta (ejemplo: `00100001`)
2. Ingresa el importe (ejemplo: `100.00`)
3. Presiona "DEPOSITAR"

**¿Qué pasa después?**

**SI TODO ESTÁ BIEN (estado = 1):**
- Verás un diálogo de ÉXITO:
  - ✅ Título: "¡Operación Exitosa!"
  - 🟢 Mensaje verde: "Depósito exitoso" (o el mensaje del servidor)
  - 🔵 Mensaje azul: "Nuevo saldo disponible - Cuenta 00100001: $ 1,100.00"

**SI HAY ERROR (estado = -1):**
- Verás un diálogo de ERROR:
  - ❌ Título: "Error en la Operación"
  - 🔴 Mensaje rojo: El error específico del servidor
  - ⚠️ Advertencia: "Por favor, verifique los datos ingresados..."

**Posibles errores:**
- Cuenta no existe
- Cuenta no está activa
- Importe debe ser mayor a 0
- Campos vacíos

---

### 5️⃣ **RETIRO**

**¿Para qué sirve?**
- Sacar dinero de una cuenta

**¿Cómo la uso?**
1. Ingresa el número de cuenta (ejemplo: `00100001`)
2. Ingresa el importe (ejemplo: `40.00`)
3. Presiona "RETIRAR"

**¿Qué pasa después?**

**SI TODO ESTÁ BIEN:**
- Diálogo de ÉXITO mostrando:
  - ✅ Retiro exitoso
  - 💰 Nuevo saldo disponible

**SI HAY ERROR:**
- Diálogo de ERROR mostrando:
  - ❌ La cuenta no existe o no está activa
  - 💸 Saldo insuficiente
  - ⚠️ Importe debe ser mayor a 0

---

### 6️⃣ **TRANSFERENCIA**

**¿Para qué sirve?**
- Transferir dinero de una cuenta a otra

**¿Cómo la uso?**
1. Ingresa la **Cuenta origen** (de dónde sale el dinero)
2. Ingresa la **Cuenta destino** (a dónde va el dinero)
3. Ingresa el **Importe** (cuánto dinero)
4. Presiona "TRANSFERIR"

**¿Qué pasa después?**

**SI TODO ESTÁ BIEN:**
- Diálogo de ÉXITO mostrando:
  - ✅ "Transferencia de $40.00 desde la cuenta 00100001 hacia la cuenta 00100002 realizada correctamente."
  - 💰 "Nuevo saldo disponible - Cuenta 00100001: $ 6,937.40"

**SI HAY ERROR:**
- Diálogo de ERROR mostrando:
  - ❌ Las cuentas no pueden ser iguales
  - ❌ La cuenta origen/destino no existe o no está activa
  - 💸 Saldo insuficiente en cuenta origen

---

## 🎨 **DISEÑO DE LOS DIÁLOGOS**

### ✅ **Diálogo de ÉXITO:**
```
┌─────────────────────────┐
│         X (cerrar)      │
│                         │
│      ✓ (verde)         │
│                         │
│  ¡Operación Exitosa!   │
│  (texto verde)          │
│                         │
│ ┌───────────────────┐  │
│ │ Mensaje principal │  │
│ │ (fondo verde      │  │
│ │  claro)           │  │
│ └───────────────────┘  │
│                         │
│ ┌───────────────────┐  │
│ │ Nuevo saldo       │  │
│ │ disponible        │  │
│ │ (fondo azul claro)│  │
│ └───────────────────┘  │
└─────────────────────────┘
```

### ❌ **Diálogo de ERROR:**
```
┌─────────────────────────┐
│         X (cerrar)      │
│                         │
│      ✗ (rojo)          │
│                         │
│  Error en la Operación │
│  (texto rojo)           │
│                         │
│ ┌───────────────────┐  │
│ │ Mensaje de error  │  │
│ │ (fondo rojo claro)│  │
│ └───────────────────┘  │
│                         │
│ ┌───────────────────┐  │
│ │ ⚠ Advertencia     │  │
│ │ (fondo naranja)   │  │
│ └───────────────────┘  │
└─────────────────────────┘
```

---

## 🧪 **DATOS DE PRUEBA**

### Cuentas de ejemplo:
```
00100001  - Cuenta activa con saldo
00100002  - Cuenta activa con saldo
```

### Operaciones de prueba:

**1. DEPÓSITO:**
- Cuenta: `00100001`
- Importe: `100.00`
- ✅ Resultado: Depósito exitoso

**2. RETIRO:**
- Cuenta: `00100001`
- Importe: `40.00`
- ✅ Resultado: Retiro exitoso (si hay saldo)

**3. TRANSFERENCIA:**
- Cuenta origen: `00100001`
- Cuenta destino: `00100002`
- Importe: `40.00`
- ✅ Resultado: Transferencia exitosa (si hay saldo)

**4. CONSULTAR MOVIMIENTOS:**
- Cuenta: `00100001`
- ✅ Resultado: Lista de movimientos

---

## ❓ **PREGUNTAS FRECUENTES**

### P: ¿Por qué dice "Error en la Operación" pero no me dice qué falló?
**R:** El servidor **SÍ** te envía el error específico. El diálogo muestra:
- **Mensaje rojo**: El error exacto (ej: "Saldo insuficiente", "Cuenta no existe")
- **Advertencia naranja**: Sugerencia de qué verificar

### P: ¿Qué significa "estado = -1"?
**R:** El servidor responde con dos valores:
- `estado = 1` → **Operación exitosa** ✅
- `estado = -1` → **Operación fallida** ❌

Cuando es `-1`, el servidor también envía un mensaje de error específico.

### P: ¿Puedo usar cualquier número de cuenta?
**R:** NO, solo las cuentas que existen en la base de datos y están **ACTIVAS**.

### P: ¿Qué pasa si ingreso letras en el importe?
**R:** El campo solo acepta números y punto decimal (ej: `100.50`)

### P: ¿Por qué el texto se veía gris?
**R:** Ya lo corregí. Ahora los textos son:
- **Negro oscuro (#212121)** cuando no tienes el cursor ahí
- **Negro puro (#000000)** cuando estás escribiendo

---

## 🔧 **SOLUCIÓN DE PROBLEMAS**

### "No veo la imagen Monster en el menú"
✅ **Ya corregido** - Ahora muestra la foto redonda de Monster

### "No puedo leer lo que escribo"
✅ **Ya corregido** - Textos ahora en negro

### "No me dice qué error pasó"
✅ **Sí lo hace** - Revisa el mensaje en el recuadro rojo del diálogo

### "Sale 'Credenciales incorrectas'"
🔍 **Verifica:**
- Usuario: `MONSTER` (todo en mayúsculas)
- Contraseña: `MONSTER9` (sin espacios, sin el 8)

---

¡Eso es todo! La aplicación ya está completamente funcional 🎉
