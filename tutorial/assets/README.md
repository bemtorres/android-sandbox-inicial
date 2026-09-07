# Propuesta de imágenes — Tutorial Sandbox

Esta carpeta `tutorial/assets/images/` es el **repositorio de screenshots** del tutorial. No está vinculada a Gradle (igual que `tutorial/`), solo es web estática.

## Convención de nombres

```
lab-textinputs-preview.png        → Android Studio Layout Preview (Design)
lab-textinputs-emulator.png       → Emulador con 6 EditText y teclado email/phone
lab-textinputs-error.png          → Toast + setError() en rojo
lab-datetime-dialog.png           → DatePickerDialog / TimePickerDialog abiertos
lab-buttons-chip.png              → ChipGroup con selección + tvStatus
lab-selection-result.png          → CheckBox + Radio + Switch → tvResult
lab-feedback-progress.png         → ProgressBar 70% + indeterminado
lab-webview-loaded.png            → WebView cargando developer.android.com + progress
lab-dialog-toast.png              → AlertDialog + Toast LENGTH_SHORT
auth-login.png                    → MainActivity con email/pass + card interna
auth-register.png                 → RegisterActivity con 6 campos
auth-intro.png                    → IntroActivity ¡Hola, Javier!
home-hub.png                      → HomeActivity con 7 cards
arquitectura-project.png          → Project pane Android (app/src/main)
arquitectura-manifest.png         → AndroidManifest.xml abierto
strings-files.jpg                → 4 archivos strings.xml (values, -en, -pt, -es)
navegacion-flow.png               → Diagrama flechas Main → Register → Intro → Home
validacion-error.png              → Campo con setError rojo + Toast
debug-logcat.png                  → Logcat filtrado SANDBOX_* con v/d/i/w/e colores
```

## Tamaño recomendado
- **Layout Preview**: 1280×800, recorte del Design split.
- **Emulador**: Pixel 7 (1080×2400) en modo portrait, escala 50%, fondo blanco.
- **Formato**: PNG, sin compresión extra. Peso < 500KB.

## Cómo capturar desde Android Studio
1. **Layout Preview**: Abre `res/layout/activity_*.xml` → pestaña `Design` → `View Options → Show Layout Decorations` → captura con `Windows + Shift + S`.
2. **Emulador**: Ejecuta `Run → Run 'app'` → espera Home → captura con cámara del Emulator (ícono cámara) o `adb exec-out screencap -p > assets/images/home-hub.png`.
3. **Logcat**: Abre `View → Tool Windows → Logcat` → filtra `SANDBOX_*` → captura con seleccione colores visibles.

## Uso en HTML
Cada página tiene un placeholder:

```html
<figure class="group rounded-2xl border-2 border-dashed border-zinc-300 bg-zinc-50 overflow-hidden">
  <div class="aspect-[16/10] flex flex-col items-center justify-center p-8 text-center">
    <div class="w-12 h-12 rounded-xl bg-white border border-zinc-200 flex items-center justify-center">📷</div>
    <p class="mt-3 font-medium text-sm">Espacio para screenshot</p>
    <p class="text-xs text-zinc-500">Guarda como assets/images/nombre.png</p>
  </div>
  <img src="assets/images/nombre.png" alt="descripción" class="hidden w-full object-cover group-[.loaded]:block" onload="this.parentElement.classList.add('loaded'); this.previousElementSibling.style.display='none'" />
  <figcaption class="px-4 py-2 bg-white border-t border-zinc-200 text-xs text-zinc-500">Fig. — descripción</figcaption>
</figure>
```

Al colocar el PNG con el nombre correcto, el placeholder se oculta automáticamente.

## Checklist
- [ ] auth-login.png, auth-register.png, auth-intro.png, home-hub.png
- [ ] lab-textinputs-*.png (3), lab-datetime-*.png (2), lab-buttons-*.png (2), lab-selection-*.png (2), lab-feedback-*.png (2), lab-webview-*.png (2), lab-dialog-*.png (2)
- [ ] arquitectura-project.png, strings-files.jpg, navegacion-flow.png, validacion-error.png, debug-logcat.png
