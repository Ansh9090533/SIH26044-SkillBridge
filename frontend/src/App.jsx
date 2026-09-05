import { useState } from 'react';
import './App.css';

function App() {
  const [skillsInput, setSkillsInput] = useState('Node.js, Express, MongoDB, Git');
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleMatch = async () => {
    setLoading(true);
    const studentSkills = skillsInput.split(',').map(s => s.trim());

    try {
      const res = await fetch('http://localhost:5000/api/matching/match-jobs', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ studentSkills, limit: 5 })
      });
      const data = await res.json();
      if (data.success) {
        setJobs(data.data.results);
      }
    } catch (err) {
      console.error("Error fetching matches:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
      <h1>SkillBridge Job Matching</h1>
      <div style={{ marginBottom: '1rem' }}>
        <label>Enter your skills (comma-separated):</label><br />
        <input 
          type="text" 
          value={skillsInput} 
          onChange={(e) => setSkillsInput(e.target.value)}
          style={{ width: '300px', padding: '8px', marginTop: '5px' }}
        />
        <button onClick={handleMatch} style={{ padding: '8px 16px', marginLeft: '10px' }}>
          {loading ? 'Matching...' : 'Find Matches'}
        </button>
      </div>

      <div>
        <h2>Matching Opportunities</h2>
        {jobs.map((job) => (
          <div key={job.id} style={{ border: '1px solid #ccc', margin: '10px 0', padding: '10px', borderRadius: '5px' }}>
            <h3>{job.title} - {job.company} ({job.matchPercentage}% Match)</h3>
            <p><strong>Matching Skills:</strong> {job.matchingSkills.join(', ')}</p>
            <p><strong>Missing Skills:</strong> {job.missingSkills.join(', ') || 'None'}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;