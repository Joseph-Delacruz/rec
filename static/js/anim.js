// anim.js
document.addEventListener("DOMContentLoaded", () => {
  const card = document.getElementById("card");
  // entrada suave
  setTimeout(() => {
    card.style.opacity = "1";
    card.style.transform = "translateY(0)";
  }, 150);

  // animacion en los inputs (resaltar al focus)
  const inputs = document.querySelectorAll(".input-field");
  inputs.forEach(inp => {
    inp.addEventListener("focus", () => {
      inp.style.transition = "box-shadow .15s, transform .12s";
      inp.style.transform = "translateY(-2px)";
    });
    inp.addEventListener("blur", () => {
      inp.style.transform = "translateY(0)";
    });
  });

  // efecto pequeño en el botón al submit (ripple)
  const btn = document.getElementById("btnSubmit");
  btn.addEventListener("click", (e) => {
    const circle = document.createElement("span");
    const rect = btn.getBoundingClientRect();
    const size = Math.max(rect.width, rect.height);
    circle.style.width = circle.style.height = size + "px";
    circle.style.position = "absolute";
    circle.style.left = (e.clientX - rect.left - size/2) + "px";
    circle.style.top = (e.clientY - rect.top - size/2) + "px";
    circle.style.background = "rgba(255,255,255,0.3)";
    circle.style.borderRadius = "50%";
    circle.style.transform = "scale(0)";
    circle.style.transition = "transform .5s, opacity .8s";
    circle.style.pointerEvents = "none";
    btn.style.position = "relative";
    btn.appendChild(circle);
    requestAnimationFrame(() => { circle.style.transform = "scale(1)"; circle.style.opacity = "0"; });
    setTimeout(()=> circle.remove(), 900);
  });

  // validación sencilla de ejemplo: evita envío si codigo vacío
  const form = document.querySelector("form");
  form.addEventListener("submit", (ev) => {
    const codigo = form.querySelector("input[name='codigo']").value.trim();
    if (!codigo) {
      ev.preventDefault();
      alert("Ingrese un código antes de registrar.");
    }
  });
});
