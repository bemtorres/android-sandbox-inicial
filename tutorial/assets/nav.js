/**
 * Sandbox Android Tutorial - Sistema de Navegación, Componentes & Estilo shadcn/ui
 * Incluye:
 * - Resaltado de sintaxis con Highlight.js + Paleta de Colores Shadcn/UI.
 * - Lightbox con Zoom interactivo para capturas de pantalla.
 * - Numeración de líneas estilo terminal dark (#09090b).
 * - Botón de copiar código con feedback interactivo.
 * - Sidebar dinámico responsive con navegación activa.
 */

(function () {
  const NAV_SECTIONS = [
    {
      title: "Inicio & Base",
      items: [
        { href: "index.html", label: "Dashboard", badge: "Core" },
        { href: "strings-i18n.html", label: "Strings & i18n", badge: "187 keys" },
        { href: "navegacion.html", label: "Navegación & datos", badge: "Intents" },
      ],
    },
    {
      title: "Flujo Core",
      items: [
        { href: "auth.html", label: "Login / Registro / Intro" },
        { href: "home.html", label: "Home Hub" },
      ],
    },
    {
      title: "Laboratorios (1 al 10)",
      items: [
        { href: "labs.html", label: "Índice de Laboratorios" },
        { href: "lab-textinputs.html", label: "Lab 1 · TextInputs" },
        { href: "lab-datetime.html", label: "Lab 2 · Date & Time" },
        { href: "lab-buttons.html", label: "Lab 3 · Buttons & Chip" },
        { href: "lab-selection.html", label: "Lab 4 · Selection" },
        { href: "lab-feedback.html", label: "Lab 5 · Feedback & Rating" },
        { href: "lab-webview.html", label: "Lab 6 · WebView" },
        { href: "lab-dialog.html", label: "Lab 7 · Dialog & Logger" },
        { href: "lab-layouts.html", label: "Lab 8 · Layouts" },
        { href: "lab-scroll.html", label: "Lab 9 · ScrollView" },
        { href: "lab-recycler.html", label: "Lab 10 · RecyclerView" },
      ],
    },
    {
      title: "Técnico & Guías",
      items: [
        { href: "vistas.html", label: "Catálogo de vistas" },
        { href: "validacion.html", label: "Estrategia Validación" },
        { href: "debug.html", label: "Build & Logcat" },
        { href: "migracion-kotlin.html", label: "⚡ Propuesta Kotlin", badge: "New" },
        { href: "propuesta-imagenes.html", label: "Guía de Capturas" },
      ],
    },
  ];

  function getCurrentPage() {
    const path = window.location.pathname;
    const file = path.split("/").pop() || "index.html";
    return file.toLowerCase();
  }

  function renderSidebar() {
    const sidebar = document.getElementById("sidebar");
    if (!sidebar) return;

    const currentPage = getCurrentPage();

    let navHtml = `
      <!-- Header Mobile Drawer -->
      <div class="flex items-center justify-between lg:hidden mb-4 pb-3 border-b border-zinc-200/80">
        <div class="flex items-center gap-2.5">
          <div class="w-7 h-7 rounded-lg bg-zinc-900 text-white flex items-center justify-center font-bold text-xs shadow-xs">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M4 6h16M4 12h16M4 18h16"/></svg>
          </div>
          <p class="font-semibold text-sm text-zinc-900 tracking-tight">Android Sandbox</p>
        </div>
        <button id="closeBtn" class="p-1.5 rounded-md text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100 transition-colors" aria-label="Cerrar menú">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 6L6 18M6 6l12 12"/></svg>
        </button>
      </div>

      <!-- Quick Search / Breadcrumb Badge -->
      <div class="hidden lg:block mb-4 px-2">
        <div class="flex items-center justify-between text-xs py-1.5 px-2.5 rounded-lg border border-zinc-200 bg-zinc-50 text-zinc-600 font-medium">
          <span class="flex items-center gap-1.5">
            <span class="w-2 h-2 rounded-full bg-emerald-500"></span>
            Android Sandbox Docs
          </span>
          <span class="text-[10px] font-mono bg-white px-1.5 py-0.5 rounded border border-zinc-200">v1.0</span>
        </div>
      </div>

      <!-- Navigation Tree -->
      <nav class="space-y-5 text-[13px] pb-10">
    `;

    NAV_SECTIONS.forEach((sec) => {
      navHtml += `
        <div class="space-y-1.5">
          <p class="px-2 text-[11px] font-semibold text-zinc-400 uppercase tracking-wider">
            ${sec.title}
          </p>
          <ul class="space-y-0.5">
      `;

      sec.items.forEach((item) => {
        const isActive = currentPage === item.href.toLowerCase();
        const activeClasses = isActive
          ? "bg-zinc-100 text-zinc-900 font-medium shadow-2xs"
          : "text-zinc-600 hover:text-zinc-900 hover:bg-zinc-50 transition-colors";

        navHtml += `
          <li>
            <a href="${item.href}" class="group flex items-center justify-between py-1.5 px-2.5 rounded-md ${activeClasses}">
              <span class="truncate">${item.label}</span>
              ${
                item.badge
                  ? `<span class="text-[10px] font-medium px-1.5 py-0.2 rounded-md ${
                      isActive
                        ? "bg-zinc-900 text-white"
                        : "bg-zinc-100 text-zinc-500 group-hover:bg-zinc-200 group-hover:text-zinc-700"
                    }">${item.badge}</span>`
                  : isActive
                  ? '<span class="w-1.5 h-1.5 rounded-full bg-zinc-900"></span>'
                  : ""
              }
            </a>
          </li>
        `;
      });

      navHtml += `
          </ul>
        </div>
      `;
    });

    navHtml += `</nav>`;
    sidebar.innerHTML = navHtml;
  }

  function setupDrawer() {
    const menuBtn = document.getElementById("menuBtn");
    const closeBtn = document.getElementById("closeBtn");
    const sidebar = document.getElementById("sidebar");
    const overlay = document.getElementById("overlay");

    function openDrawer() {
      if (sidebar) sidebar.classList.remove("-translate-x-full");
      if (overlay) overlay.classList.remove("hidden");
      document.body.classList.add("overflow-hidden", "lg:overflow-auto");
    }

    function closeDrawer() {
      if (sidebar) sidebar.classList.add("-translate-x-full");
      if (overlay) overlay.classList.add("hidden");
      document.body.classList.remove("overflow-hidden", "lg:overflow-auto");
    }

    if (menuBtn) menuBtn.addEventListener("click", openDrawer);
    if (closeBtn) closeBtn.addEventListener("click", closeDrawer);
    if (overlay) overlay.addEventListener("click", closeDrawer);

    document.addEventListener("keydown", (e) => {
      if (e.key === "Escape") closeDrawer();
    });
  }

  /**
   * Paleta de Colores de Sintaxis Shadcn/UI
   */
  function injectSyntaxStyles() {
    if (document.getElementById("shadcn-syntax-colors")) return;

    const style = document.createElement("style");
    style.id = "shadcn-syntax-colors";
    style.textContent = `
      /* Paleta Shadcn / JetBrains para Código */
      .hljs { background: transparent !important; color: #f4f4f5 !important; }
      .hljs-keyword { color: #f472b6 !important; font-weight: 600; } /* Keywords: fun, val, class, public, return */
      .hljs-type, .hljs-class, .hljs-title.class_ { color: #38bdf8 !important; } /* Types / Classes */
      .hljs-string { color: #4ade80 !important; } /* Strings */
      .hljs-number, .hljs-literal, .hljs-boolean { color: #fb923c !important; } /* Numbers & Booleans */
      .hljs-function .hljs-title, .hljs-title.function_ { color: #60a5fa !important; } /* Functions */
      .hljs-comment, .hljs-quote { color: #71717a !important; font-style: italic; } /* Comments */
      .hljs-meta, .hljs-meta .hljs-keyword { color: #fbbf24 !important; } /* Annotations @Override */
      .hljs-tag { color: #94a3b8 !important; } /* XML Tag brackets */
      .hljs-name { color: #f43f5e !important; font-weight: 600; } /* XML Tag Names: TextView, LinearLayout */
      .hljs-attr { color: #38bdf8 !important; } /* XML Attributes: android:id */
      .hljs-punctuation { color: #94a3b8 !important; } /* Punctuation */
      .hljs-symbol, .hljs-selector-tag { color: #c084fc !important; }
      .hljs-built_in { color: #38bdf8 !important; }
      .hljs-variable, .hljs-template-variable { color: #f4f4f5 !important; }
    `;
    document.head.appendChild(style);
  }

  /**
   * Carga dinámica segura de Highlight.js si no existe
   */
  function ensureHighlightJs(callback) {
    if (window.hljs) {
      callback();
      return;
    }

    const script = document.createElement("script");
    script.src = "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js";
    script.onload = () => {
      // Cargar soporte de Kotlin adicional si hace falta
      const ktScript = document.createElement("script");
      ktScript.src = "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/kotlin.min.js";
      ktScript.onload = callback;
      ktScript.onerror = callback;
      document.head.appendChild(ktScript);
    };
    script.onerror = callback;
    document.head.appendChild(script);
  }

  function initCodeEnhancements() {
    injectSyntaxStyles();

    ensureHighlightJs(() => {
      document.querySelectorAll("pre").forEach((pre) => {
        const code = pre.querySelector("code");
        if (!code) return;

        // Limpieza de entidades y preservación del texto original para copia (incluso en tabs ocultas)
        const rawCode = code.textContent || code.innerText;

        // Auto-detectar lenguaje si no tiene clase
        if (!code.className || !code.className.includes("language-")) {
          const trimmed = rawCode.trim();
          if (trimmed.startsWith("<") || trimmed.includes("android:")) {
            code.classList.add("language-xml");
          } else if (trimmed.includes("fun ") || trimmed.includes("val ") || trimmed.includes("var ") || trimmed.includes("::class")) {
            code.classList.add("language-kotlin");
          } else if (trimmed.includes("./gradlew") || trimmed.includes("adb ")) {
            code.classList.add("language-bash");
          } else {
            code.classList.add("language-java");
          }
        }

        // Aplicar resaltado oficial seguro de Highlight.js
        if (window.hljs && typeof window.hljs.highlightElement === "function") {
          try {
            window.hljs.highlightElement(code);
          } catch (e) {
            console.warn("Highlight error:", e);
          }
        }

        // Numeración de líneas estilo terminal dark
        if (!pre.querySelector(".cm-gutter")) {
          const gutter = document.createElement("div");
          gutter.className = "cm-gutter";
          const lines = rawCode.split("\n");
          const n = lines[lines.length - 1] === "" ? lines.length - 1 : lines.length;

          for (let i = 1; i <= n; i++) {
            const s = document.createElement("span");
            s.textContent = i;
            gutter.appendChild(s);
          }

          pre.style.position = "relative";
          pre.insertBefore(gutter, code);
        }

        // Botón de Copiar (estilo shadcn)
        if (!pre.querySelector(".copy-btn")) {
          const copyBtn = document.createElement("button");
          copyBtn.className =
            "copy-btn absolute top-2.5 right-2.5 p-1.5 rounded-md bg-zinc-800/90 hover:bg-zinc-700 text-zinc-300 hover:text-white text-[11px] font-medium border border-zinc-700/60 opacity-0 group-hover:opacity-100 transition-all flex items-center gap-1.5 shadow-sm";
          copyBtn.innerHTML = `
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect width="14" height="14" x="8" y="8" rx="2" ry="2"/><path d="M4 16c-1.1 0-2-.9-2-2V4c0-1.1.9-2 2-2h10c1.1 0 2 .9 2 2"/></svg>
            <span class="copy-text">Copiar</span>
          `;

          copyBtn.addEventListener("click", () => {
            navigator.clipboard.writeText(rawCode).then(() => {
              const copyText = copyBtn.querySelector(".copy-text");
              if (copyText) copyText.textContent = "¡Copiado!";
              copyBtn.classList.add("text-emerald-400", "border-emerald-500/50");
              setTimeout(() => {
                if (copyText) copyText.textContent = "Copiar";
                copyBtn.classList.remove("text-emerald-400", "border-emerald-500/50");
              }, 2000);
            });
          });

          pre.classList.add("group");
          pre.appendChild(copyBtn);
        }
      });
    });
  }

  // Switch de pestañas estilo segmented control de shadcn (soporte multi-tab flexible)
  window.switchTab = function (arg1, arg2, arg3) {
    // Modo 1: switchTab(containerId, tabName) con data-tab
    const container = document.getElementById(arg1);
    if (container && container.querySelector("[data-tab]")) {
      const tabName = arg2;
      container.querySelectorAll(".tab-btn").forEach((btn) => {
        if (btn.getAttribute("data-tab") === tabName) {
          btn.classList.add("bg-white", "text-zinc-900", "shadow-xs", "font-semibold");
          btn.classList.remove("text-zinc-500", "hover:text-zinc-900");
        } else {
          btn.classList.remove("bg-white", "text-zinc-900", "shadow-xs", "font-semibold");
          btn.classList.add("text-zinc-500", "hover:text-zinc-900");
        }
      });

      container.querySelectorAll(".tab-pane").forEach((pane) => {
        if (pane.getAttribute("data-tab") === tabName) {
          pane.classList.remove("hidden");
        } else {
          pane.classList.add("hidden");
        }
      });
      return;
    }

    // Modo 2: switchTab(activeTabId, inactiveTabId, btnElement)
    const activeContent = document.getElementById(arg1);
    const inactiveContent = document.getElementById(arg2);
    const btnElement = arg3;

    if (activeContent) activeContent.classList.remove("hidden");
    if (inactiveContent) inactiveContent.classList.add("hidden");

    if (btnElement && btnElement.parentElement) {
      btnElement.parentElement.querySelectorAll("button").forEach((btn) => {
        btn.classList.remove("bg-white", "text-zinc-900", "shadow-xs");
        btn.classList.add("text-zinc-600", "hover:text-zinc-900");
      });
      btnElement.classList.add("bg-white", "text-zinc-900", "shadow-xs");
      btnElement.classList.remove("text-zinc-600", "hover:text-zinc-900");
    }
  };

  /**
   * Sistema de Zoom / Lightbox Interactivo para Imágenes (Estilo shadcn/ui)
   */
  let lightboxEl = null;
  let currentZoom = 1;
  let isDragging = false;
  let startX = 0, startY = 0;
  let translateX = 0, translateY = 0;

  function createLightbox() {
    if (lightboxEl) return;

    lightboxEl = document.createElement("div");
    lightboxEl.id = "shadcn-image-lightbox";
    lightboxEl.className =
      "fixed inset-0 z-50 bg-black/85 backdrop-blur-md hidden flex flex-col justify-between p-4 sm:p-6 select-none opacity-0 transition-opacity duration-200";

    lightboxEl.innerHTML = `
      <!-- Top Bar Controls -->
      <div class="flex items-center justify-between w-full max-w-5xl mx-auto z-10 text-white">
        <div class="flex items-center gap-2">
          <span class="text-xs font-mono px-2.5 py-1 rounded-md bg-zinc-800/80 border border-zinc-700/60 text-zinc-300" id="lightbox-filename">imagen.png</span>
          <span class="text-xs font-mono px-2 py-1 rounded-md bg-zinc-900 text-zinc-400" id="lightbox-zoom-level">100%</span>
        </div>

        <div class="flex items-center gap-1.5 bg-zinc-900/90 p-1 rounded-lg border border-zinc-700/60 shadow-lg">
          <button id="lightbox-zoom-out" class="p-1.5 rounded-md hover:bg-zinc-800 text-zinc-300 hover:text-white transition-colors" title="Reducir (-) [o rueda abajo]">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/><line x1="8" y1="11" x2="14" y2="11"/></svg>
          </button>
          <button id="lightbox-zoom-reset" class="px-2 py-1 text-xs font-medium rounded-md hover:bg-zinc-800 text-zinc-300 hover:text-white transition-colors" title="Restablecer (1:1)">
            1:1
          </button>
          <button id="lightbox-zoom-in" class="p-1.5 rounded-md hover:bg-zinc-800 text-zinc-300 hover:text-white transition-colors" title="Ampliar (+) [o rueda arriba]">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/><line x1="11" y1="8" x2="11" y2="14"/><line x1="8" y1="11" x2="14" y2="11"/></svg>
          </button>
          <div class="w-px h-4 bg-zinc-700 mx-1"></div>
          <button id="lightbox-close" class="p-1.5 rounded-md hover:bg-rose-500/20 text-zinc-300 hover:text-rose-300 transition-colors" title="Cerrar (Esc)">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
          </button>
        </div>
      </div>

      <!-- Main Zoom Canvas Area -->
      <div id="lightbox-canvas" class="flex-1 w-full flex items-center justify-center overflow-hidden my-auto relative cursor-grab active:cursor-grabbing">
        <img id="lightbox-img" src="" alt="Zoom Preview" class="max-w-[90vw] max-h-[80vh] object-contain rounded-lg shadow-2xl transition-transform duration-100 ease-out will-change-transform" />
      </div>

      <!-- Bottom Caption Bar -->
      <div class="w-full max-w-2xl mx-auto text-center z-10">
        <p id="lightbox-caption" class="text-xs text-zinc-300 bg-zinc-900/80 px-4 py-2 rounded-full border border-zinc-700/60 inline-block shadow-md">
          Vista previa interactiva
        </p>
      </div>
    `;

    document.body.appendChild(lightboxEl);

    // Controles de Eventos
    const imgEl = document.getElementById("lightbox-img");
    const canvasEl = document.getElementById("lightbox-canvas");
    const zoomLevelEl = document.getElementById("lightbox-zoom-level");
    const btnIn = document.getElementById("lightbox-zoom-in");
    const btnOut = document.getElementById("lightbox-zoom-out");
    const btnReset = document.getElementById("lightbox-zoom-reset");
    const btnClose = document.getElementById("lightbox-close");

    function updateTransform() {
      if (!imgEl) return;
      imgEl.style.transform = `translate(${translateX}px, ${translateY}px) scale(${currentZoom})`;
      if (zoomLevelEl) zoomLevelEl.textContent = `${Math.round(currentZoom * 100)}%`;
      if (canvasEl) {
        if (currentZoom > 1) {
          canvasEl.classList.remove("cursor-grab");
          canvasEl.classList.add("cursor-move");
        } else {
          canvasEl.classList.remove("cursor-move");
          canvasEl.classList.add("cursor-grab");
        }
      }
    }

    function setZoom(newZoom) {
      currentZoom = Math.min(Math.max(newZoom, 0.5), 4);
      if (currentZoom <= 1) {
        translateX = 0;
        translateY = 0;
      }
      updateTransform();
    }

    btnIn.addEventListener("click", (e) => {
      e.stopPropagation();
      setZoom(currentZoom + 0.3);
    });

    btnOut.addEventListener("click", (e) => {
      e.stopPropagation();
      setZoom(currentZoom - 0.3);
    });

    btnReset.addEventListener("click", (e) => {
      e.stopPropagation();
      setZoom(1);
    });

    btnClose.addEventListener("click", (e) => {
      e.stopPropagation();
      closeLightbox();
    });

    // Zoom con Rueda del Ratón
    canvasEl.addEventListener("wheel", (e) => {
      e.preventDefault();
      const delta = e.deltaY < 0 ? 0.2 : -0.2;
      setZoom(currentZoom + delta);
    }, { passive: false });

    // Doble Click para Alternar Zoom
    canvasEl.addEventListener("dblclick", (e) => {
      e.preventDefault();
      if (currentZoom > 1.2) {
        setZoom(1);
      } else {
        setZoom(2);
      }
    });

    // Arrastre / Pan cuando hay zoom
    canvasEl.addEventListener("mousedown", (e) => {
      if (e.target === btnClose || e.target.closest("button")) return;
      isDragging = true;
      startX = e.clientX - translateX;
      startY = e.clientY - translateY;
    });

    window.addEventListener("mousemove", (e) => {
      if (!isDragging) return;
      translateX = e.clientX - startX;
      translateY = e.clientY - startY;
      updateTransform();
    });

    window.addEventListener("mouseup", () => {
      isDragging = false;
    });

    // Cerrar al pulsar el fondo (fuera de la imagen y controles)
    lightboxEl.addEventListener("click", (e) => {
      if (e.target === lightboxEl || e.target === canvasEl) {
        closeLightbox();
      }
    });

    document.addEventListener("keydown", (e) => {
      if (!lightboxEl || lightboxEl.classList.contains("hidden")) return;
      if (e.key === "Escape") closeLightbox();
      if (e.key === "+" || e.key === "=") setZoom(currentZoom + 0.25);
      if (e.key === "-") setZoom(currentZoom - 0.25);
      if (e.key === "0") setZoom(1);
    });
  }

  function openLightbox(src, captionText, fileName) {
    createLightbox();
    const imgEl = document.getElementById("lightbox-img");
    const captionEl = document.getElementById("lightbox-caption");
    const filenameEl = document.getElementById("lightbox-filename");

    if (imgEl) imgEl.src = src;
    if (captionEl) captionEl.textContent = captionText || "Captura de pantalla de Android Sandbox";
    if (filenameEl) filenameEl.textContent = fileName || src.split("/").pop() || "captura.png";

    currentZoom = 1;
    translateX = 0;
    translateY = 0;
    if (imgEl) imgEl.style.transform = `translate(0px, 0px) scale(1)`;
    const zoomLevelEl = document.getElementById("lightbox-zoom-level");
    if (zoomLevelEl) zoomLevelEl.textContent = "100%";

    lightboxEl.classList.remove("hidden");
    lightboxEl.classList.remove("opacity-0");
    lightboxEl.classList.add("opacity-100");
    document.body.classList.add("overflow-hidden");
  }

  function closeLightbox() {
    if (!lightboxEl) return;
    lightboxEl.classList.remove("opacity-100");
    lightboxEl.classList.add("opacity-0");
    setTimeout(() => {
      lightboxEl.classList.add("hidden");
      document.body.classList.remove("overflow-hidden");
    }, 150);
  }

  function injectMobileStyles() {
    if (document.getElementById("sandbox-mobile-styles")) return;
    const style = document.createElement("style");
    style.id = "sandbox-mobile-styles";
    style.textContent = `
      .mobile-frame {
        position: relative;
        display: inline-flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        max-width: 100%;
        max-height: 100%;
        background: #09090b;
        border: 7px solid #18181b;
        border-radius: 2rem;
        box-shadow: 0 16px 32px -8px rgba(0, 0, 0, 0.4), 0 4px 12px -2px rgba(0, 0, 0, 0.25), inset 0 0 0 1px rgba(255, 255, 255, 0.12);
        overflow: hidden;
        box-sizing: border-box;
        margin: 0 auto;
      }
      .mobile-frame .mobile-notch {
        position: absolute;
        top: 6px;
        left: 50%;
        transform: translateX(-50%);
        width: 3rem;
        height: 0.35rem;
        background: #27272a;
        border-radius: 9999px;
        z-index: 20;
        box-shadow: 0 1px 2px rgba(0,0,0,0.6);
        pointer-events: none;
      }
      .mobile-frame .mobile-home-bar {
        position: absolute;
        bottom: 5px;
        left: 50%;
        transform: translateX(-50%);
        width: 3.25rem;
        height: 0.22rem;
        background: rgba(255, 255, 255, 0.4);
        border-radius: 9999px;
        z-index: 20;
        pointer-events: none;
      }
      .mobile-frame img,
      img.mobile-gif {
        display: block;
        max-height: 16.5rem;
        width: auto;
        max-width: 100%;
        object-fit: contain;
        border-radius: 1.45rem;
      }
    `;
    document.head.appendChild(style);
  }

  function initImages() {
    injectMobileStyles();
    const images = document.querySelectorAll("figure img, main img");
    images.forEach((img) => {
      img.classList.add("cursor-zoom-in", "transition-transform", "duration-150", "hover:scale-[1.015]");

      const src = img.getAttribute("src") || "";
      const isMobileGif = img.classList.contains("mobile-gif") || src.toLowerCase().endsWith(".gif");

      if (isMobileGif && !img.closest(".mobile-frame")) {
        img.classList.add("mobile-gif");
        const wrapper = document.createElement("div");
        wrapper.className = "mobile-frame";

        const notch = document.createElement("div");
        notch.className = "mobile-notch";

        const homeBar = document.createElement("div");
        homeBar.className = "mobile-home-bar";

        img.parentNode.insertBefore(wrapper, img);
        wrapper.appendChild(notch);
        wrapper.appendChild(img);
        wrapper.appendChild(homeBar);
      }

      function handleSuccess() {
        img.classList.remove("hidden");
        img.style.display = "block";
        const figure = img.closest("figure") || img.parentElement;
        if (figure) {
          figure.classList.add("loaded");
          figure.classList.remove("border-dashed");
          const placeholder = figure.querySelector(".img-placeholder");
          if (placeholder) {
            placeholder.style.display = "none";
          }
        }
      }

      function handleError() {
        const srcAttr = img.getAttribute("src");
        if (srcAttr && !img.dataset.triedFallback) {
          img.dataset.triedFallback = "true";
          if (srcAttr.endsWith(".png")) {
            img.src = srcAttr.replace(/\.png$/i, ".jpg");
            return;
          } else if (srcAttr.endsWith(".jpg")) {
            img.src = srcAttr.replace(/\.jpg$/i, ".png");
            return;
          } else if (srcAttr.endsWith(".jpeg")) {
            img.src = srcAttr.replace(/\.jpeg$/i, ".png");
            return;
          } else if (srcAttr.endsWith(".gif")) {
            img.src = srcAttr.replace(/\.gif$/i, ".png");
            return;
          }
        }
        img.style.display = "none";
        const figure = img.closest("figure") || img.parentElement;
        if (figure) {
          figure.classList.remove("loaded");
          const placeholder = figure.querySelector(".img-placeholder");
          if (placeholder) placeholder.style.display = "flex";
        }
      }

      img.addEventListener("load", handleSuccess);
      img.addEventListener("error", handleError);

      img.addEventListener("click", (e) => {
        e.preventDefault();
        e.stopPropagation();
        const figure = img.closest("figure");
        let captionText = "";
        let fileName = img.src.split("/").pop();

        if (figure) {
          const figcaption = figure.querySelector("figcaption");
          if (figcaption) captionText = figcaption.textContent.trim();
        }

        openLightbox(img.currentSrc || img.src, captionText, fileName);
      });

      if (img.complete) {
        if (img.naturalWidth > 0) {
          handleSuccess();
        } else {
          handleError();
        }
      }
    });
  }

  document.addEventListener("DOMContentLoaded", () => {
    renderSidebar();
    setupDrawer();
    initCodeEnhancements();
    initImages();
    createLightbox();
  });
})();
