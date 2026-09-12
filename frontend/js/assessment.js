/* ==========================================================================
   SkillBridge — Assessment page & test engine
   Vanilla JS. No backend yet — everything below uses mock/sample data
   structured so a real API can be swapped in later (see the
   `fetchQuestions()` / `submitResult()` stubs at the bottom).
   ========================================================================== */

(function () {
  "use strict";

  /* ------------------------------------------------------------------ */
  /* 1. Mobile sidebar toggle (shared pattern across dashboard pages)     */
  /* ------------------------------------------------------------------ */

  const sidebar = document.getElementById("sidebar");
  const menuToggle = document.getElementById("menuToggle");
  const sidebarBackdrop = document.getElementById("sidebarBackdrop");

  function openSidebar() {
    sidebar.classList.add("open");
    sidebarBackdrop.classList.add("open");
  }
  function closeSidebar() {
    sidebar.classList.remove("open");
    sidebarBackdrop.classList.remove("open");
  }
  if (menuToggle) menuToggle.addEventListener("click", openSidebar);
  if (sidebarBackdrop) sidebarBackdrop.addEventListener("click", closeSidebar);

  /* ------------------------------------------------------------------ */
  /* 2. Mock question bank                                                */
  /* ------------------------------------------------------------------ */

  const QUESTION_BANK = [
    // ---- Java ----
    { id: "j1", skill: "Java", text: "Which keyword is used to inherit a class in Java?", options: ["extends", "implements", "inherits", "super"], correct: 0 },
    { id: "j2", skill: "Java", text: "Which of these is not a primitive data type in Java?", options: ["int", "float", "String", "boolean"], correct: 2 },
    { id: "j3", skill: "Java", text: "What does the JVM stand for?", options: ["Java Virtual Machine", "Java Verified Method", "Java Variable Model", "Java Visual Machine"], correct: 0 },
    { id: "j4", skill: "Java", text: "Which method is the entry point of a Java application?", options: ["start()", "run()", "main()", "init()"], correct: 2 },

    // ---- SQL ----
    { id: "s1", skill: "SQL", text: "Which SQL clause is used to filter groups of rows?", options: ["WHERE", "HAVING", "GROUP", "FILTER"], correct: 1 },
    { id: "s2", skill: "SQL", text: "Which statement is used to remove a table from a database?", options: ["DELETE TABLE", "REMOVE TABLE", "DROP TABLE", "TRUNCATE DATABASE"], correct: 2 },
    { id: "s3", skill: "SQL", text: "Which JOIN returns rows only when there is a match in both tables?", options: ["LEFT JOIN", "RIGHT JOIN", "INNER JOIN", "FULL OUTER JOIN"], correct: 2 },
    { id: "s4", skill: "SQL", text: "Which keyword sorts the result set of a query?", options: ["SORT BY", "ORDER BY", "GROUP BY", "ARRANGE BY"], correct: 1 },

    // ---- DBMS ----
    { id: "d1", skill: "DBMS", text: "Which normal form removes partial dependency on a composite key?", options: ["1NF", "2NF", "3NF", "BCNF"], correct: 1 },
    { id: "d2", skill: "DBMS", text: "A primary key can contain how many NULL values?", options: ["Zero", "One", "Many", "Unlimited"], correct: 0 },
    { id: "d3", skill: "DBMS", text: "Which property of a transaction ensures it is all-or-nothing?", options: ["Consistency", "Isolation", "Atomicity", "Durability"], correct: 2 },
    { id: "d4", skill: "DBMS", text: "What kind of key uniquely identifies a row and is used to link tables?", options: ["Candidate key", "Foreign key", "Composite key", "Super key"], correct: 1 },

    // ---- DSA ----
    { id: "a1", skill: "DSA", text: "What is the time complexity of binary search on a sorted array?", options: ["O(n)", "O(log n)", "O(n log n)", "O(1)"], correct: 1 },
    { id: "a2", skill: "DSA", text: "Which data structure uses LIFO (Last In First Out) order?", options: ["Queue", "Stack", "Linked List", "Tree"], correct: 1 },
    { id: "a3", skill: "DSA", text: "Which sorting algorithm has the best average-case time complexity?", options: ["Bubble Sort", "Selection Sort", "Merge Sort", "Insertion Sort"], correct: 2 },
    { id: "a4", skill: "DSA", text: "In a graph, what is used to represent connections efficiently for sparse graphs?", options: ["Adjacency Matrix", "Adjacency List", "Incidence Matrix", "Distance Matrix"], correct: 1 },

    // ---- Web Development ----
    { id: "w1", skill: "Web Development", text: "Which HTML tag is used to link an external stylesheet?", options: ["<style>", "<css>", "<link>", "<script>"], correct: 2 },
    { id: "w2", skill: "Web Development", text: "Which CSS property controls the space between elements and their border?", options: ["margin", "padding", "spacing", "gap"], correct: 1 },
    { id: "w3", skill: "Web Development", text: "Which HTTP method is typically used to submit form data that changes server state?", options: ["GET", "POST", "HEAD", "OPTIONS"], correct: 1 },
    { id: "w4", skill: "Web Development", text: "Which JavaScript method adds an element to the end of an array?", options: ["push()", "pop()", "shift()", "concat()"], correct: 0 },

    // ---- Problem Solving / basic programming ----
    { id: "p1", skill: "Problem Solving", text: "What will `10 % 3` evaluate to in most programming languages?", options: ["0", "1", "3", "3.33"], correct: 1 },
    { id: "p2", skill: "Problem Solving", text: "Which approach breaks a problem into overlapping subproblems and stores results?", options: ["Greedy algorithm", "Dynamic programming", "Brute force", "Divide and conquer"], correct: 1 },
    { id: "p3", skill: "Problem Solving", text: "What is the output of a well-formed recursive function missing a base case?", options: ["A single value", "A syntax error", "Infinite recursion / stack overflow", "Zero"], correct: 2 },
    { id: "p4", skill: "Problem Solving", text: "Which of these best describes Big-O notation?", options: ["Exact runtime in seconds", "Upper bound on growth rate of an algorithm", "Memory address size", "Number of lines of code"], correct: 1 },
  ];

  const SKILLS = [
    { key: "Java", icon: "fa-mug-hot", desc: "Core syntax, OOP concepts and the Java standard library.", difficulty: "Intermediate", count: 4 },
    { key: "SQL", icon: "fa-database", desc: "Queries, joins, aggregation and schema fundamentals.", difficulty: "Beginner", count: 4 },
    { key: "DBMS", icon: "fa-server", desc: "Normalization, transactions, keys and database design.", difficulty: "Intermediate", count: 4 },
    { key: "DSA", icon: "fa-diagram-project", desc: "Data structures, algorithms and complexity analysis.", difficulty: "Advanced", count: 4 },
    { key: "Web Development", icon: "fa-code", desc: "HTML, CSS, JavaScript and core web fundamentals.", difficulty: "Beginner", count: 4 },
    { key: "Problem Solving", icon: "fa-lightbulb", desc: "Logical reasoning and general programming concepts.", difficulty: "Intermediate", count: 4 },
  ];

  // Mock "previous progress" per skill — in a real build this comes from the backend.
  const SKILL_PROGRESS = {
    "Java": { status: "completed", score: 80 },
    "SQL": { status: "completed", score: 60 },
    "DBMS": { status: "completed", score: 75 },
    "DSA": { status: "completed", score: 55 },
    "Web Development": { status: "not_started", score: null },
    "Problem Solving": { status: "not_started", score: null },
  };

  /* ------------------------------------------------------------------ */
  /* 3. Render category cards                                             */
  /* ------------------------------------------------------------------ */

  const categoryGrid = document.getElementById("categoryGrid");

  function renderCategories() {
    categoryGrid.innerHTML = "";
    SKILLS.forEach((skill) => {
      const prog = SKILL_PROGRESS[skill.key] || { status: "not_started", score: null };
      const statusBadge =
        prog.status === "completed"
          ? `<span class="badge badge-green"><i class="fa-solid fa-circle-check"></i> ${prog.score}%</span>`
          : `<span class="badge badge-muted">Not started</span>`;
      const buttonLabel = prog.status === "completed" ? "Retake" : "Start";

      const card = document.createElement("div");
      card.className = "card category-card";
      card.innerHTML = `
        <div class="cat-head">
          <div class="cat-icon"><i class="fa-solid ${skill.icon}" aria-hidden="true"></i></div>
          ${statusBadge}
        </div>
        <div>
          <h3>${skill.key}</h3>
          <p class="cat-desc">${skill.desc}</p>
        </div>
        <div class="cat-facts">
          <span><i class="fa-solid fa-list-check"></i> ${skill.count} Qs</span>
          <span><i class="fa-solid fa-gauge-high"></i> ${skill.difficulty}</span>
        </div>
        <div class="cat-footer">
          <button class="btn btn-secondary btn-sm btn-block start-category" data-skill="${skill.key}">
            ${buttonLabel} <i class="fa-solid fa-arrow-right"></i>
          </button>
        </div>
      `;
      categoryGrid.appendChild(card);
    });

    categoryGrid.querySelectorAll(".start-category").forEach((btn) => {
      btn.addEventListener("click", () => {
        const skillKey = btn.getAttribute("data-skill");
        startTest(skillKey, `${skillKey} Assessment`);
      });
    });
  }

  renderCategories();

  /* ------------------------------------------------------------------ */
  /* 4. Test engine state                                                 */
  /* ------------------------------------------------------------------ */

  const testOverlay = document.getElementById("testOverlay");
  const testTitleEl = document.getElementById("testTitle");
  const testSubEl = document.getElementById("testSub");
  const testProgressFill = document.getElementById("testProgressFill");
  const testProgressLabel = document.getElementById("testProgressLabel");
  const qNumEl = document.getElementById("qNum");
  const qSkillBadge = document.getElementById("qSkillBadge");
  const qTextEl = document.getElementById("qText");
  const optionsList = document.getElementById("optionsList");
  const qGrid = document.getElementById("qGrid");
  const prevBtn = document.getElementById("prevBtn");
  const nextBtn = document.getElementById("nextBtn");
  const submitBtn = document.getElementById("submitBtn");
  const markReviewBtn = document.getElementById("markReviewBtn");
  const exitTestBtn = document.getElementById("exitTest");

  const confirmModal = document.getElementById("confirmModal");
  const cancelSubmitBtn = document.getElementById("cancelSubmit");
  const confirmSubmitBtn = document.getElementById("confirmSubmit");
  const modalAnswered = document.getElementById("modalAnswered");
  const modalUnanswered = document.getElementById("modalUnanswered");
  const modalReview = document.getElementById("modalReview");

  let state = {
    assessmentName: "",
    questions: [],
    currentIndex: 0,
    answers: {},       // { questionId: optionIndex }
    review: {},         // { questionId: true }
    startTime: null,
  };

  function fetchQuestions(skillFilter) {
    // Stub: replace with a real API call, e.g. `fetch('/api/assessment/questions?skill=...')`
    if (!skillFilter) return QUESTION_BANK.slice(0, 12); // main mixed assessment
    return QUESTION_BANK.filter((q) => q.skill === skillFilter);
  }

  function startTest(skillFilter, title) {
    const questions = fetchQuestions(skillFilter);
    if (!questions.length) return;

    state = {
      assessmentName: title || "Main Skill Assessment",
      questions,
      currentIndex: 0,
      answers: {},
      review: {},
      startTime: Date.now(),
    };

    testTitleEl.textContent = state.assessmentName;
    testSubEl.textContent = skillFilter
      ? `Focused assessment on ${skillFilter}.`
      : "Answer every question you can — you can skip and come back.";

    buildQuestionGrid();
    renderQuestion();
    testOverlay.classList.add("open");
    document.body.style.overflow = "hidden";
  }

  document.getElementById("startMainAssessment").addEventListener("click", () => {
    startTest(null, "Main Skill Assessment");
  });

  // If arriving from the Targeted Assessments page (assessment.html?skill=DSA),
  // jump straight into that skill's focused test.
  (function autoStartFromQuery() {
    const params = new URLSearchParams(window.location.search);
    const skillParam = params.get("skill");
    if (skillParam) {
      const match = SKILLS.find((s) => s.key.toLowerCase() === skillParam.toLowerCase());
      if (match) startTest(match.key, `${match.key} Assessment`);
    }
  })();

  function exitTest() {
    const answeredCount = Object.keys(state.answers).length;
    if (answeredCount > 0 && !confirm("Exit the assessment? Your progress will be lost.")) {
      return;
    }
    testOverlay.classList.remove("open");
    document.body.style.overflow = "";
  }
  exitTestBtn.addEventListener("click", exitTest);

  /* ------------------------------------------------------------------ */
  /* 5. Rendering the current question                                    */
  /* ------------------------------------------------------------------ */

  function renderQuestion() {
    const total = state.questions.length;
    const idx = state.currentIndex;
    const q = state.questions[idx];

    qNumEl.textContent = `Question ${idx + 1}`;
    qSkillBadge.textContent = q.skill;
    qTextEl.textContent = q.text;

    optionsList.innerHTML = "";
    q.options.forEach((optText, optIdx) => {
      const selected = state.answers[q.id] === optIdx;
      const item = document.createElement("button");
      item.type = "button";
      item.className = "option-item" + (selected ? " selected" : "");
      item.innerHTML = `
        <span class="opt-marker">${selected ? '<i class="fa-solid fa-check"></i>' : ""}</span>
        <span>${optText}</span>
      `;
      item.addEventListener("click", () => {
        state.answers[q.id] = optIdx;
        renderQuestion();
        updateProgress();
        updateQuestionGrid();
      });
      optionsList.appendChild(item);
    });

    prevBtn.disabled = idx === 0;
    nextBtn.innerHTML = idx === total - 1
      ? 'Finish <i class="fa-solid fa-flag-checkered"></i>'
      : 'Next <i class="fa-solid fa-arrow-right"></i>';

    const isMarked = !!state.review[q.id];
    markReviewBtn.classList.toggle("active", isMarked);
    markReviewBtn.innerHTML = isMarked
      ? '<i class="fa-solid fa-flag"></i> Marked for Review'
      : '<i class="fa-regular fa-flag"></i> Mark for Review';

    testProgressLabel.textContent = `Question ${idx + 1} of ${total}`;
    updateProgress();
    updateQuestionGrid();
  }

  function updateProgress() {
    const total = state.questions.length;
    const answered = Object.keys(state.answers).length;
    const pct = total ? Math.round((answered / total) * 100) : 0;
    testProgressFill.style.width = pct + "%";
  }

  prevBtn.addEventListener("click", () => {
    if (state.currentIndex > 0) {
      state.currentIndex -= 1;
      renderQuestion();
    }
  });

  nextBtn.addEventListener("click", () => {
    if (state.currentIndex < state.questions.length - 1) {
      state.currentIndex += 1;
      renderQuestion();
    } else {
      openConfirmModal();
    }
  });

  markReviewBtn.addEventListener("click", () => {
    const q = state.questions[state.currentIndex];
    state.review[q.id] = !state.review[q.id];
    renderQuestion();
  });

  /* ------------------------------------------------------------------ */
  /* 6. Question navigator grid                                           */
  /* ------------------------------------------------------------------ */

  function buildQuestionGrid() {
    qGrid.innerHTML = "";
    state.questions.forEach((q, i) => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.textContent = i + 1;
      btn.addEventListener("click", () => {
        state.currentIndex = i;
        renderQuestion();
      });
      qGrid.appendChild(btn);
    });
    updateQuestionGrid();
  }

  function updateQuestionGrid() {
    Array.from(qGrid.children).forEach((btn, i) => {
      const q = state.questions[i];
      btn.classList.toggle("current", i === state.currentIndex);
      btn.classList.toggle("answered", state.answers[q.id] !== undefined && !state.review[q.id]);
      btn.classList.toggle("review", !!state.review[q.id]);
    });
  }

  /* ------------------------------------------------------------------ */
  /* 7. Submit flow                                                       */
  /* ------------------------------------------------------------------ */

  function openConfirmModal() {
    const total = state.questions.length;
    const answered = Object.keys(state.answers).length;
    const reviewCount = Object.keys(state.review).filter((id) => state.review[id]).length;

    modalAnswered.textContent = answered;
    modalUnanswered.textContent = total - answered;
    modalReview.textContent = reviewCount;
    confirmModal.classList.add("open");
  }

  submitBtn.addEventListener("click", openConfirmModal);
  cancelSubmitBtn.addEventListener("click", () => confirmModal.classList.remove("open"));

  confirmSubmitBtn.addEventListener("click", () => {
    confirmModal.classList.remove("open");
    const result = scoreAssessment();
    submitResult(result);
    window.location.href = "result.html";
  });

  function scoreAssessment() {
    const total = state.questions.length;
    let correct = 0;
    const skillTotals = {};
    const skillCorrect = {};

    state.questions.forEach((q) => {
      skillTotals[q.skill] = (skillTotals[q.skill] || 0) + 1;
      const given = state.answers[q.id];
      if (given !== undefined && given === q.correct) {
        correct += 1;
        skillCorrect[q.skill] = (skillCorrect[q.skill] || 0) + 1;
      }
    });

    const answeredCount = Object.keys(state.answers).length;
    const incorrect = answeredCount - correct;
    const unanswered = total - answeredCount;
    const accuracy = total ? Math.round((correct / total) * 100) : 0;
    const elapsedSeconds = state.startTime ? Math.round((Date.now() - state.startTime) / 1000) : 0;

    const skillBreakdown = Object.keys(skillTotals).map((skill) => ({
      skill,
      total: skillTotals[skill],
      correct: skillCorrect[skill] || 0,
      percent: Math.round(((skillCorrect[skill] || 0) / skillTotals[skill]) * 100),
    }));

    return {
      assessmentName: state.assessmentName,
      studentName: "Student Name",
      completedAt: new Date().toISOString(),
      totalQuestions: total,
      correct,
      incorrect,
      unanswered,
      accuracy,
      timeTakenSeconds: elapsedSeconds,
      skillBreakdown,
    };
  }

  function submitResult(result) {
    // Stub: replace with `fetch('/api/assessment/submit', { method: 'POST', body: JSON.stringify(result) })`
    try {
      sessionStorage.setItem("skillbridge_result", JSON.stringify(result));
    } catch (e) {
      console.warn("Could not persist result to sessionStorage:", e);
    }
  }

})();
