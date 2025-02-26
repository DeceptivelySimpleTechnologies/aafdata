//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.
//TODO: Get live authentication token
//document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

console.log(`app.js executing! No DOMContentLoaded listener added yet.`);

//NOTE: Constructors for both EntityTables defined in index.html are called here

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('DOMContentLoaded', () => {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  console.log(`app.js still executing! DOMContentLoaded listener now added to page.`);

  //NOTE: Populate the definitions table with data by adding custom listener and dispatching custom event
  const definitionsEvent = new CustomEvent('entityTableCreated', {
    detail: { entityTableDefinitions }
  });

  console.log(`app.js still executing! entityTableCreated event dispatched ...`);
  entityTableDefinitions.dispatchEvent(definitionsEvent);

  const selectedRow = entityTableDefinitions.querySelector('tr').rowIndex[0];

  //NOTE: Populate the attributes table with data by adding custom listener and dispatching custom event
  const attributesEvent = new CustomEvent('entityTableRowClicked', {
    detail: { selectedRow }
  });

  console.log(`app.js still executing! entityTableRowClicked event dispatched ...`);
  entityTableAttributes.dispatchEvent(attributesEvent);

  //TODO: Add click event listener to each row in the first table??? (unless passing the EntityTable reference is sufficient)

});
