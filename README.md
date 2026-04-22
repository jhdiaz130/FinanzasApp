FinanzasApp 📱

Este repositorio contiene el código fuente y la documentación de la aplicación FinanzasApp, desarrollada para la gestión de ingresos, gastos y presupuestos personales.

Uso del proyecto

Este proyecto fue probado usando el SDK de Android versión 36.

Con Android Studio

Abra el proyecto en Android Studio.

Para generar el proyecto vaya a:

Build > Generate App Bundles or APKs > Build APK(s)

Luego puede ejecutar la aplicación en:

un emulador
un dispositivo físico Android

Desde la línea de comandos

Para compilar el proyecto desde la terminal ejecute:

./gradlew assembleDebug

Esto generará el APK en la ruta:

app/build/outputs/apk/debug/app-debug.apk

Pruebas

Puede ejecutar la aplicación instalando el APK generado en su dispositivo físico o en un emulador.

Ruta del APK:

app/build/outputs/apk/debug/

Archivo:

app-debug.apk
Estructura del proyecto
app/
 ├── src/
 │   ├── main/
 │   │   ├── java/com/example/finanzasapp
 │   │   ├── res/layout
 │   │   ├── res/drawable
 │   │   └── AndroidManifest.xml
Autor

Jheiner Diaz
