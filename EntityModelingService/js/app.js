//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//TODO: Get live authentication token
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('entityTablePopulated', handleEntityTablePopulated);

function handleEntityTablePopulated(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  if (event.detail.tableId === 'entitytypedefinition') {
    const selectedRow = entityTableDefinitions.shadowRoot.querySelectorAll('tbody tr')[0];
    // console.log(`Selected row id: ${selectedRow.querySelectorAll('td')[0].innerText}`);
    selectedRow.setAttribute('class', 'selected');
    const associatedAttributes = entityTableDefinitions.fetchData(entityTableDefinitions.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22EntityTypeDefinitionId%22%20%3D%20' + '${selectedRow.querySelectorAll(\'td\')[0].innerText}', entityTableDefinitions.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 200, 1).then((data) => {
      let associatedAttributeIds = data.filter((item) => item.EntityTypeDefinitionId + ',');
      return associatedAttributeIds;
    });
    entityTableAttributes.refreshData(entityTableAttributes.getAttribute('baseUrl') || 'http://localhost:8080/entityTypeAttributes/', entityTableAttributes.getAttribute('entityTypeName') || 'EntityTypeAttribute', '%22EntitySubtypeId%22%20%3D%2067', entityTableAttributes.getAttribute('sortClause') || '%22Ordinal%22%20ASC', entityTableAttributes.getAttribute('pageSize') || 20, entityTableAttributes.getAttribute('pageNumber') || 1)
  }
}
