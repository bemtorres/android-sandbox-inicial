# Sandbox Android — Aprende widget a widget

> **Proyecto educativo en Java** para dominar Android sin acoplar módulos. `Login → Registro → Intro → Home Hub → 7 labs aislados` con validación real, `Toast`/`Dialog`/`Logcat` y todo el texto externalizado `common_` / `acXXXX_` en 3 idiomas (`es` / `en` / `pt`).

[![Android](https://img.shields.io/badge/Android-API%2027--37-3DDC84?logo=android)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Java-11-007396?logo=java)](https://openjdk.java.net/)
[![Material3](https://img.shields.io/badge/Material-3-6750A4)](https://m3.material.io/)
[![Gradle](https://img.shields.io/badge/Gradle-9.5-02303A?logo=gradle)](https://gradle.org/)
[![Pages](https://img.shields.io/badge/Tutorial-GitHub%20Pages-000?logo=github)](tutorial/)

**Tutorial web** (Tailwind v4, CodeMirror-like, multi-página, responsive) en [`tutorial/`](tutorial/) — desplegable a Pages con [`.github/workflows/static.yml`](.github/workflows/static.yml).

---

## Qué es

Un **Hub + 7 Activities autónomas**. Cada lab prueba un grupo de vistas sin romper los demás. Si crashea Lab WebView, Login y Home siguen funcionando. Ideal para trastear `inputType`, `TextWatcher`, `ChipGroup`, `WebViewClient`, `Handler`, etc.

**Flujo guiado:**
```
MainActivity (Login, launcher, exported=true)
  → RegisterActivity (6 inputTypes + TextWatcher)
    → IntroActivity (¡Hola, %1$s! vía Intent + Prefs)
      → HomeActivity (Hub, 7 MaterialCardView)
        → .sandbox.TextInputs / DateTime / Buttons / Selection / Feedback / WebView / DialogToast
```

## Objetivos de aprendizaje

- `TextView`, `TextInputLayout` + `TextInputEditText` (`text`, `textEmailAddress`, `phone`, `number`, `textPassword`, `numberPassword`)
- `Button`, `ImageButton` (`contentDescription`), `Chip` + `ChipGroup singleSelection`
- `CheckBox`, `RadioButton` + `RadioGroup`, `SwitchMaterial`
- `ProgressBar` (horizontal / indeterminado), `RatingBar`, `Spinner` (`ArrayAdapter`)
- `WebView` + `WebViewClient` (`onPageStarted/Finished`, `canGoBack`)
- `AlertDialog.Builder`, `Toast LENGTH_SHORT/LONG`, `Log.v/d/i/w/e`

Validación centralizada en `utils/ValidationUtils.java:11` + feedback UI `setError()` + `Toast` + `Log`.

## Stack

| Capa | Versión |
|------|---------|
| Android Gradle Plugin | 9.3.2 |
| compileSdk / targetSdk | 37 |
| minSdk | 27 |
| Java | 11 |
| AppCompat | 1.6.1 |
| Material | 1.10.0 |
| ConstraintLayout | 2.1.4 |
| Theme | `Theme.Material3.DayNight.NoActionBar` |

## Estructura

```
Sandbox/
├── app/src/main/
│   ├── AndroidManifest.xml          # MainActivity launcher + 11 exported=false + INTERNET
│   ├── java/com/example/sandbox/
│   │   ├── MainActivity.java        # Login
│   │   ├── RegisterActivity.java    # Registro
│   │   ├── IntroActivity.java       # Bienvenida
│   │   ├── HomeActivity.java        # Hub 7 cards
│   │   ├── utils/ValidationUtils.java
│   │   ├── utils/LoggerUtils.java
│   │   ├── utils/Prefs.java         # SharedPreferences sandbox_prefs
│   │   └── sandbox/                 # 7 labs aislados
│   │       ├── TextInputsActivity.java
│   │       ├── DateTimeActivity.java
│   │       ├── ButtonsActivity.java
│   │       ├── SelectionActivity.java
│   │       ├── FeedbackActivity.java
│   │       ├── WebViewActivity.java
│   │       └── DialogToastActivity.java
│   ├── res/layout/                  # 11 xml (4 core + 7 labs)
│   ├── res/values/strings.xml       # 187 strings, es default
│   ├── res/values-es/strings.xml    # es explícito
│   ├── res/values-en/strings.xml    # en
│   └── res/values-pt/strings.xml    # pt
└── tutorial/                        # Web estática, NO vinculada a Gradle
    ├── index.html                   # Dashboard
    ├── arquitectura.html
    ├── strings-i18n.html
    ├── navegacion.html              # Cómo cambiar vista + pasar datos
    ├── auth.html / home.html
    ├── labs.html + lab-*.html (7)
    ├── vistas.html / validacion.html / debug.html / propuesta-imagenes.html
    └── assets/images/               # Screenshots (ver propuesta-imagenes.html)
```

`settings.gradle` solo incluye `:app` — `tutorial/` es web pura con Tailwind v4 vía CDN.

## Convención de strings

Todo texto en `strings.xml`. Cero literales en XML/Java (`grep android:text="` solo devuelve `@string/`).

- `common_*` → reutilizable (39): `common_app_name`, `common_hint_email`, `common_btn_validate`, `common_err_required`
- `acXXXX_*` → específico de layout (`ac` + 4 letras): `acmain_` (activity_main), `acregi_` (register), `acintr_` (intro), `achome_` (home), `actinp_` (text_inputs), `acdati_` (date_time), `acbutt_` (buttons), `acsele_` (selection), `acfeed_` (feedback), `acwebv_` (webview), `acdito_` (dialog_toast)

## Internacionalización

Mismo set de 187 keys en 4 archivos. El sistema resuelve por locale:

- `values/strings.xml` → default (es)
- `values-es/strings.xml` → es
- `values-en/strings.xml` → en
- `values-pt/strings.xml` → pt

Placeholders `%1$s` intactos. Cambiar idioma: Ajustes → Sistema → Idioma o `adb shell setprop persist.sys.locale en-US && adb reboot`.

## Instalación y ejecución

```bash
# Clonar
git clone https://github.com/usuario/Sandbox.git
cd Sandbox

# Compilar
./gradlew assembleDebug

# Instalar en emulador/dispositivo (con adb)
./gradlew installDebug

# Ver logs por lab
adb logcat -s SANDBOX_LOGIN SANDBOX_HOME SANDBOX_DIALOG
```

Requisitos: Android Studio Ladybug+, JDK 11, emulador Pixel 7 API 27+.

## Navegación y paso de datos

```java
// Cambiar de vista (explícito)
startActivity(new Intent(MainActivity.this, RegisterActivity.class)
  .putExtra("email", email));

// Recibir
String email = getIntent().getStringExtra("email");
if(email==null) email = Prefs.getUser(this); // fallback SharedPreferences

// Dentro de la misma Activity
tvResult.setText(getString(R.string.acsele_result_ok, res));
```

Flujo real documentado en [`tutorial/navegacion.html`](tutorial/navegacion.html) (Intent, Bundle, Parcelable, `SharedPreferences`, `ActivityResultLauncher`, `onBackPressed` + `WebView.canGoBack`).

## Tutorial web

En [`tutorial/`](tutorial/) — 16 páginas, responsive (drawer + overlay), tablas con scroll-x, código con estilo **CodeMirror Dracula** (`#282a36`, gutter `#21222c`, `JetBrains Mono`, numeración).

```bash
# Abrir local
npx serve tutorial
# o doble clic en tutorial/index.html
```

Páginas: `index`, `arquitectura`, `strings-i18n`, `navegacion`, `auth`, `home`, `labs` + 7 `lab-*`, `vistas`, `validacion`, `debug`, `propuesta-imagenes`.

**Imágenes:** placeholders ya insertados (borde discontinuo, `aspect-[16/10]`). Guarda PNG en `tutorial/assets/images/` con el nombre exacto (ej. `home-hub.png`, `lab-webview-loaded.png`) y aparece solo. Ver [`tutorial/propuesta-imagenes.html`](tutorial/propuesta-imagenes.html) y [`tutorial/assets/README.md`](tutorial/assets/README.md) para lista de 21 screenshots.

Despliegue a Pages: push a `main` dispara [`.github/workflows/static.yml`](.github/workflows/static.yml) (sube `tutorial/` como artifact). Activa en Settings → Pages → Source: **GitHub Actions**.

## Labs

| # | Prefix | Vistas clave |
|---|--------|--------------|
| 1 | `actinp_` | `TextView`, 6 `inputType`, `TextInputLayout.setError`, `TextWatcher` |
| 2 | `acdati_` | `DatePickerDialog`, `TimePickerDialog`, `Calendar`, `isNotFuture` |
| 3 | `acbutt_` | `Button` debounce 500ms, `ImageButton` a11y, `ChipGroup singleSelection` |
| 4 | `acsele_` | `CheckBox isChecked`, `RadioGroup getCheckedRadioButtonId`, `SwitchMaterial` |
| 5 | `acfeed_` | `ProgressBar` + `Handler`, `RatingBar`, `Spinner ArrayAdapter` |
| 6 | `acwebv_` | `WebView` + `WebViewClient onPageStarted/Finished` |
| 7 | `acdito_` | `Toast`, `AlertDialog.Builder`, `Log.v/d/i/w/e` 7 tags |

## Validación

`ValidationUtils` sin `Context` (testeable): `isRequired`, `isEmail` (`Patterns.EMAIL_ADDRESS`), `isPassword` (≥6), `isNumericPassword` (`^\d{4,6}$`), `isPhone` (`^\+?[0-9 ]{9,15}$`), `isPostalCode` (`^\d{5}$`), `isNotFuture`. UI: orden `required → formato → setError(null)` + `Toast` + `Log`.

## Licencia

MIT — úsalo para aprender, romper y volver a montar.
