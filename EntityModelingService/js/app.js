//#NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

async function fetchData() {
  const response = await fetch('http://localhost:8080/entityTypes/EntitySubtype?whereClause=%22Id%22%20%3E%200&sortClause=%22LocalizedName%22%20ASC&pageNumber=1&pageSize=20&ApiKey=6fdcf7a4-6442-4c1e-a375-9f8064ea34d0&CorrelationUuid=5595adaa-0312-4983-82e7-1127e3c43444', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'http://localhost:8082',
      'Access-Control-Allow-Credentials': 'true',
      'ApiKey': '6fdcf7a4-6442-4c1e-a375-9f8064ea34d0',
      'CorrelationUuid': '83b0361c-108f-4fe6-b95b-fc6f6d645e76'
//      'Cookie' : 'Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw'
    },
    credentials: 'include' //NOTE: This will include cookies with the request
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

//  const data = await response.json();
  const responseText = await response.text();
  console.log('Raw response text:', responseText);
  data = JSON.parse(responseText);
  return data;
}

function displayData(data) {
  const table = document.getElementById('data-table');
  data.EntityData.forEach(item => {
    const row = table.insertRow();
    Object.values(item).forEach(text => {
      const cell = row.insertCell();
      cell.textContent = text;
    });
  });
}

document.addEventListener('DOMContentLoaded', async () => {
  try {
    const data = await fetchData();
//    console.log(data.toString());
    displayData(data);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
});
