/* ==========================================================================
   SkillBridge — Result page rendering
   Reads the result written to sessionStorage by js/assessment.js. If the
   student lands here directly (no assessment taken yet in this session),
   a representative mock result is shown instead so the page is never empty.
   ========================================================================== */

(function () {
  "use strict";

  /* ---- Mobile sidebar toggle -------------------------------------- */
  const sidebar = document.getElementById("sidebar");
  const menuToggle = document.getElementById("menuToggle");
  const sidebarBackdrop = document.getElementById("sidebarBackdrop");
  function openSidebar() { sidebar.classList.add("open"); sidebarBackdrop.classList.add("open"); }
  function closeSidebar() { sidebar.classList.remove("open"); sidebarBackdrop.classList.remove("open"); }
  if (menuToggle) menuToggle.addEventListener("click", openSidebar);
  if (sidebarBackdrop) sidebarBackdrop.addEventListener("click", closeSidebar);

  /* ---- Load result (real or mock fallback) -------------------------- */

  const FALLBACK_RESULT = {
    assessmentName: "Main Skill Assessment",
    studentName: "Student Name",
    completedAt: new Date().toISOString(),
    totalQuestions: 12,
    correct: 9,
    incorrect: 2,
    unanswered: 1,
    accuracy: 72,
    timeTakenSeconds: 913,
    skillBreakdown: [
      { skill: "Java", total: 4, correct: 3, percent: 80 },
      { skill: "SQL", total: 4, correct: 2, percent: 60 },
      { skill: "DBMS", total: 4, correct: 3, percent: 75 },
      { skill: "DSA", total: 4, correct: 2, percent: 55 },
    ],
  };

  function loadResult() {
    try {
      const raw = sessionStorage.getItem("skillbridge_result");
      if (raw) return JSON.parse(raw);
    } catch (e) {
      console.warn("Could not read stored result:", e);
    }
    return FALLBACK_RESULT;
  }

  const result = loadResult();

  /* ---- Header ------------------------------------------------------- */

  document.getElementById("assessmentNameEl").textContent = result.assessmentName || "Main Skill Assessment";
  const completedDate = result.completedAt ? new Date(result.completedAt) : new Date();
  const dateStr = completedDate.toLocaleDateString(undefined, { year: "numeric", month: "long", day: "numeric" });
  document.getElementById("studentLineEl").textContent = `${result.studentName || "Student Name"} · Completed on ${dateStr}`;

  /* ---- Score ring ----------------------------------------------------- */

  const accuracy = typeof result.accuracy === "number" ? result.accuracy : 0;
  const scoreRing = document.getElementById("scoreRing");
  scoreRing.style.background = `conic-gradient(var(--blue-500) 0 ${accuracy}%, var(--bg-input) ${accuracy}% 100%)`;
  document.getElementById("scorePctEl").textContent = accuracy + "%";

  let perfMsg = "Keep going!";
  let perfSub = "Every attempt sharpens your fundamentals — review the gaps below and try again.";
  if (accuracy >= 85) {
    perfMsg = "Excellent work!";
    perfSub = "You're performing strongly across most tested skills. A couple of small gaps remain.";
  } else if (accuracy >= 65) {
    perfMsg = "Good effort!";
    perfSub = "You're building a solid technical foundation — a few focused areas will boost this score significantly.";
  } else if (accuracy >= 40) {
    perfMsg = "You're on your way.";
    perfSub = "Core basics are forming. Focus on the weaker skills below before your next attempt.";
  }
  document.getElementById("performanceMsg").textContent = perfMsg;
  document.getElementById("performanceSub").textContent = perfSub;
  document.getElementById("accuracyBadge").textContent = `Accuracy: ${accuracy}%`;

  const totalSeconds = result.timeTakenSeconds || 0;
  const mins = Math.floor(totalSeconds / 60);
  const secs = totalSeconds % 60;
  document.getElementById("timeBadge").textContent = `Time: ${mins}m ${secs}s`;

  /* ---- Summary strip -------------------------------------------------- */

  const summaryGrid = document.getElementById("summaryGrid");
  const summaryItems = [
    { label: "Total Questions", value: result.totalQuestions, icon: "fa-list-check", color: "var(--blue-500)" },
    { label: "Correct Answers", value: result.correct, icon: "fa-circle-check", color: "var(--green-500)" },
    { label: "Incorrect Answers", value: result.incorrect, icon: "fa-circle-xmark", color: "var(--red-500)" },
    { label: "Accuracy", value: accuracy + "%", icon: "fa-bullseye", color: "var(--blue-500)" },
    { label: "Time Taken", value: `${mins}m ${secs}s`, icon: "fa-clock", color: "var(--amber-500)" },
  ];
  summaryGrid.innerHTML = summaryItems.map((item) => `
    <div class="card summary-item">
      <div class="s-icon" style="background:rgba(255,255,255,.04); color:${item.color}">
        <i class="fa-solid ${item.icon}"></i>
      </div>
      <div class="s-value">${item.value}</div>
      <div class="s-label">${item.label}</div>
    </div>
  `).join("");

  /* ---- Skill-wise performance ------------------------------------------ */

  const skillPerfList = document.getElementById("skillPerfList");
  const breakdown = result.skillBreakdown || [];
  skillPerfList.innerHTML = breakdown.map((s) => {
    const cls = s.percent >= 70 ? "" : s.percent >= 50 ? "amber" : "red";
    return `
      <div class="progress-row">
        <div class="progress-label"><span class="name">${s.skill}</span><span class="value">${s.percent}%</span></div>
        <div class="progress-track"><div class="progress-fill ${cls}" style="width:${s.percent}%"></div></div>
      </div>
    `;
  }).join("");

  /* ---- Skill gap summary ------------------------------------------------ */

  const strong = breakdown.filter((s) => s.percent >= 70).sort((a, b) => b.percent - a.percent);
  const weak = breakdown.filter((s) => s.percent < 70).sort((a, b) => a.percent - b.percent);

  const strongSkillsList = document.getElementById("strongSkillsList");
  strongSkillsList.innerHTML = strong.length
    ? strong.map((s) => `<li><i class="fa-solid fa-check" style="color:var(--green-500)"></i> ${s.skill} fundamentals (${s.percent}%)</li>`).join("")
    : `<li>Keep attempting assessments to build up strong areas.</li>`;

  const weakSkillsList = document.getElementById("weakSkillsList");
  weakSkillsList.innerHTML = weak.length
    ? weak.map((s) => `<li><i class="fa-solid fa-arrow-trend-up" style="color:var(--amber-500)"></i> ${s.skill} (${s.percent}%)</li>`).join("")
    : `<li>No major weak areas detected in this attempt.</li>`;

  const nextStepsList = document.getElementById("nextStepsList");
  nextStepsList.innerHTML = weak.length
    ? weak.slice(0, 3).map((s) => `<li><i class="fa-solid fa-book" style="color:var(--blue-500)"></i> Take the ${s.skill} targeted assessment</li>`).join("")
      + `<li><i class="fa-solid fa-diagram-project" style="color:var(--blue-500)"></i> Review your full skill gap report</li>`
    : `<li><i class="fa-solid fa-briefcase" style="color:var(--blue-500)"></i> Explore matched opportunities</li>`;

})();
