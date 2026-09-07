/**
 * Sandbox Android Tutorial - Sistema de Navegación y Utilidades Unificadas
 */

(function () {
  const NAV_SECTIONS = [
    {
      title: "Inicio & Base",
      items: [
        { href: "index.html", label: "Dashboard" },
        { href: "arquitectura.html", label: "Arquitectura profunda" },
        { href: "strings-i18n.html", label: "Strings & i18n" },
        { href: "navegacion.html", label: "Navegación & datos" },
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
      title: "Laboratorios (1 a 10)",
      items: [
        { href: "labs.html", label: "Índice de labs" },
        { href: "lab-textinputs.html", label: "Lab 1 · TextInputs" },
        { href: "lab-datetime.html", label: "Lab 2 · Date/Time" },
        { href: "lab-buttons.html", label: "Lab 3 · Buttons/Chip" },
        { href: "lab-selection.html", label: "Lab 4 · Selection" },
        { href: "lab-feedback.html", label: "Lab 5 · Feedback" },
        { href: "lab-webview.html", label: "Lab 6 · WebView" },
        { href: "lab-dialog.html", label: "Lab 7 · Dialog/Toast/Logger" },
        { href: "lab-layouts.html", label: "Lab 8 · Layouts" },
        { href: "lab-scroll.html", label: "Lab 9 · Lista Scroll" },
        { href: "lab-recycler.html", label: "Lab 10 · RecyclerView" },
      ],
    },
    {
      title: "Técnico & Guías",
      items: [
        { href: "vistas.html", label: "Catálogo de vistas" },
        { href: "validacion.html", label: "Validación" },
        { href: "debug.html", label: "Build & Logcat" },
        { href: "migracion-kotlin.html", label: "⚡ Propuesta Kotlin" },
        { href: "propuesta-imagenes.html", label: "Propuesta imágenes" },
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
      <div class="flex items-center justify-between lg:hidden mb-4 pb-2 border-b border-zinc-100">
        <div class="flex items-center gap-2">
          <div class="w-6 h-6 rounded-lg bg-violet-600 text-white flex items-center justify-center font-bold text-xs">Sb</div>
          <p class="font-semibold text-sm text-zinc-900">Menú del Tutorial</p>
        </div>
        <button id="closeBtn" class="p-2 rounded-xl border border-zinc-200 hover:bg-zinc-50 text-zinc-600" aria-label="Cerrar menú">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 6L6 18M6 6l12 12"/></svg>
        </button>
      </div>
      <nav class="space-y-4 text-[12px] pb-8">
    `;

    NAV_SECTIONS.forEach((sec) => {
      navHtml += `
        <div>
          <p class="font-semibold mb-2 text-zinc-900 tracking-tight flex items-center justify-between">
            <span>${sec.title}</span>
          </p>
          <ul class="space-y-1">
      `;

      sec.items.forEach((item) => {
        const isActive = currentPage === item.href.toLowerCase();
        const activeClasses = isActive
          ? "bg-violet-50 text-violet-700 font-semibold shadow-xs border border-violet-100/70"
          : "text-zinc-600 hover:text-zinc-900 hover:bg-zinc-100/70 transition-colors";

        navHtml += `
          <li>
            <a href="${item.href}" class="flex items-center justify-between py-1.5 px-2.5 rounded-lg ${activeClasses}">
              <span>${item.label}</span>
              ${isActive ? '<span class="w-1.5 h-1.5 rounded-full bg-violet-600"></span>' : ""}
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

    // Cerrar con Escape
    document.addEventListener("keydown", (e) => {
      if (e.key === "Escape") closeDrawer();
    });
  }

  function initCodeGutter() {
    if (window.hljs && typeof window.hljs.highlightAll === "function") {
      try {
        window.hljs.highlightAll();
      } catch (e) {
        console.warn("Highlight.js:", e);
      }
    }

    document.querySelectorAll("pre code").forEach((code) => {
      const pre = code.parentElement;
      if (!pre || pre.querySelector(".cm-gutter")) return;

      const gutter = document.createElement("div");
      gutter.className = "cm-gutter";
      const lines = code.textContent.split("\n");
      const n = lines[lines.length - 1] === "" ? lines.length - 1 : lines.length;

      for (let i = 1; i <= n; i++) {
        const s = document.createElement("span");
        s.textContent = i;
        gutter.appendChild(s);
      }

      pre.style.position = "relative";
      pre.insertBefore(gutter, code);
      pre.classList.add("cm-s-dracula");
    });
  }

  // Soporte para pestañas de código (Java vs Kotlin vs XML)
  window.switchTab = function (containerId, tabName) {
    const container = document.getElementById(containerId);
    if (!container) return;

    // Actualizar botones
    container.querySelectorAll(".tab-btn").forEach((btn) => {
      if (btn.getAttribute("data-tab") === tabName) {
        btn.classList.add("bg-violet-600", "text-white", "font-medium");
        btn.classList.remove("bg-zinc-800", "text-zinc-400", "hover:text-zinc-200");
      } else {
        btn.classList.remove("bg-violet-600", "text-white", "font-medium");
        btn.classList.add("bg-zinc-800", "text-zinc-400", "hover:text-zinc-200");
      }
    });

    // Actualizar paneles
    container.querySelectorAll(".tab-pane").forEach((pane) => {
      if (pane.getAttribute("data-tab") === tabName) {
        pane.classList.remove("hidden");
      } else {
        pane.classList.add("hidden");
      }
    });
  };

  document.addEventListener("DOMContentLoaded", () => {
    renderSidebar();
    setupDrawer();
    initCodeGutter();
  });
})();
