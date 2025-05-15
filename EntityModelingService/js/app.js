//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//TODO: Get live authentication token
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('entityTablePopulated', handleEntityTablePopulated);

function handleEntityTablePopulated(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');

  if (event.detail.tableId === 'entitytypedefinition') {
    const selectedRow = entityTableDefinitions.shadowRoot.querySelectorAll('tbody tr')[0];

    // console.log(`Selected row id: ${selectedRow.querySelectorAll('td')[0].innerText}`);
    selectedRow.setAttribute('class', 'selected');

    const associatedAttributes = fetchData(entityTableDefinitions.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22EntityTypeDefinitionId%22%20%3D%20' + selectedRow.querySelectorAll('td')[0].innerText, entityTableDefinitions.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 200, 1).then((data) => {
      // let associatedAttributeIds = data.EntityData.filter((item) => item.EntityTypeDefinitionId + ',');
      // let associatedAttributeIds = data.EntityData.map((item) => item.EntityTypeAttributeId);
      let associatedAttributeIds = "";

      //NOTE: Produce a URL-encoded, comma-separated list of EntityTypeAttribute Ids to pass as a whereClause to refresh the second EntityTable
      for (i = 0; i < data.EntityData.length; i++) {
        associatedAttributeIds = associatedAttributeIds + data.EntityData[i].EntityTypeAttributeId + '%2C';
      };

      associatedAttributeIds = associatedAttributeIds.slice(0, -3);

      entityTableAttributes.refreshData(entityTableAttributes.getAttribute('baseUrl') || 'http://localhost:8080/entityTypeAttributes/', entityTableAttributes.getAttribute('entityTypeName') || 'EntityTypeAttribute', '%22EntityTypeAttribute%22.%22Id%22%20IN%20%28' + associatedAttributeIds + '%29', entityTableAttributes.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 200, entityTableAttributes.getAttribute('pageNumber') || 1);
    });

    inputLocalizedName.value = selectedRow.querySelectorAll('td')[1].innerText;
    inputLocalizedDescription.value = selectedRow.querySelectorAll('td')[2].innerText;
    inputLocalizedAbbreviation.value = selectedRow.querySelectorAll('td')[3].innerText;
  }
}

async function fetchData(baseUrl, entityTypeName, whereClause, sortClause, pageSize, pageNumber) {
  const response = await fetch(baseUrl + entityTypeName + '?whereClause=' + whereClause + '&sortClause=' + sortClause + '&pageNumber=' + pageNumber + '&pageSize=' + pageSize, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'http://localhost:8082',
      'Access-Control-Allow-Credentials': 'true',
      'ApiKey': '6fdcf7a4-6442-4c1e-a375-9f8064ea34d0',
      'CorrelationUuid': '83b0361c-108f-4fe6-b95b-fc6f6d645e76'
    },
    credentials: 'include' //NOTE: This will include cookies with the request
  });

  const responseJson = await response.json();
  return responseJson;
}
