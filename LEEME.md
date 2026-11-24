# Relojes – proyecto Android

App de relojes tipo ajedrez (límite diario 8h, cuenta regresiva, resumen semanal, edición de tiempos)
con alarmas nativas: avisan 15 s antes y en 0 aunque el celular esté bloqueado.

## Opción A – Compilar con GitHub (sin instalar nada)
1. Crea un repositorio privado nuevo en github.com.
2. Sube TODO el contenido de esta carpeta (incluida la carpeta oculta `.github`).
   Desde la web: "Add file → Upload files" y arrastra los archivos/carpetas.
3. Ve a la pestaña **Actions**: se ejecuta "Build APK" solo (tarda unos 3-5 min).
4. Al terminar, entra a la ejecución y descarga **Relojes-apk** (un .zip con `app-debug.apk`).

## Opción B – Android Studio
1. File → Open → selecciona esta carpeta.
2. Espera la sincronización de Gradle.
3. Build → Build App Bundle(s) / APK(s) → Build APK(s).
4. El archivo queda en `app/build/outputs/apk/debug/app-debug.apk`.

## Instalar en el celular
1. Pasa `app-debug.apk` al celular y ábrelo.
2. Android pedirá permitir "instalar apps desconocidas" para la app desde la que lo abras (Archivos, Drive…).
3. Al abrir Relojes por primera vez, acepta el permiso de **notificaciones** (sin él no hay alarmas con la pantalla bloqueada).
4. Recomendado: Ajustes → Apps → Relojes → Batería → **Sin restricciones**, para que las alarmas no se retrasen.

La interfaz está en `app/src/main/assets/index.html` (mismo piloto HTML v3 + puente nativo).
