//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//TODO: Get live authentication token
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

//NOTE: "Wire up" the Publish button so that when it is clicked, any unpublished definitions, attributes, and associations are published
document.addEventListener('publishButtonClicked', handlePublishButtonClicked);

const buttonPublish = document.getElementById('publish');
buttonPublish.onclick = function () {
  const publishButtonClickedEvent = new CustomEvent('publishButtonClicked', {
  });
  document.dispatchEvent(publishButtonClickedEvent);
};

function handlePublishButtonClicked(event) {
  const status = document.getElementById('status');

  const result = publishData('http://localhost:8081/', 'databases/AAFCore', 'POST',
    JSON.stringify({})
  ).then((data) => {
    if (!data.hasOwnProperty("Code")) {
      status.color = "green";
      status.innerText = "Attempt to publish unpublished EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation definitions succeeded";
    }
    else {
      status.color = "red";
      status.innerText = "Attempt to publish unpublished EntityTypeDefinition, EntityTypeAttribute, and EntityTypeDefinitionEntityTypeAttributeAssociation definitions failed: " + data.Message;
    }
  });

  location.reload();
}

//NOTE: Display EntityTypeDefinition total, published, and unpublished counts
const definitions = fetchData('http://localhost:8080/entityTypes/', 'EntityTypeDefinition', '%22Id%22%20%3E%200', '%22Ordinal%22%20ASC', 500, 1).then((data) => {
  const definitionsTotal = document.getElementById('definitions-total');
  definitionsTotal.textContent = data.TotalRows;

  const definitionsPublished = document.getElementById('definitions-published');
  const definitionsUnpublished = document.getElementById('definitions-unpublished');

  let definitionsPublishedCount = 0;
  let definitionsUnpublishedCount = 0;

  for (let i = 0; i < data.TotalRows; i++) {
    if (data.EntityData[i].PublishedAtDateTimeUtc != '9999-12-31T23:59:59.999') {
      definitionsPublishedCount++;
    } else {
      definitionsUnpublishedCount++;
    }
  }

  definitionsPublished.textContent = definitionsPublishedCount;
  definitionsUnpublished.textContent = definitionsUnpublishedCount;
});

//NOTE: Display EntityTypeAttribute total, published, and unpublished counts
const attributes = fetchData('http://localhost:8080/entityTypes/', 'EntityTypeAttribute', '%22Id%22%20%3E%200', '%22Ordinal%22%20ASC', 500, 1).then((data) => {
  const attributesTotal = document.getElementById('attributes-total');
  attributesTotal.textContent = data.TotalRows;

  const attributesPublished = document.getElementById('attributes-published');
  const attributesUnpublished = document.getElementById('attributes-unpublished');

  let attributesPublishedCount = 0;
  let attributesUnpublishedCount = 0;

  for (let i = 0; i < data.TotalRows; i++) {
    if (data.EntityData[i].PublishedAtDateTimeUtc != '9999-12-31T23:59:59.999') {
      attributesPublishedCount++;
    } else {
      attributesUnpublishedCount++;
    }
  }

  attributesPublished.textContent = attributesPublishedCount;
  attributesUnpublished.textContent = attributesUnpublishedCount;
});

//NOTE: Display EntityTypeDefinitionEntityTypeAttributeAssociation total, published, and unpublished counts
const associatedAttributes = fetchData('http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22Id%22%20%3E%200', '%22Ordinal%22%20ASC', 1000, 1).then((data) => {
  const associationsTotal = document.getElementById('associations-total');
  associationsTotal.textContent = data.TotalRows;

  const associationsPublished = document.getElementById('associations-published');
  const associationsUnpublished = document.getElementById('associations-unpublished');

  let associationsPublishedCount = 0;
  let associationsUnpublishedCount = 0;

  for (let i = 0; i < data.TotalRows; i++) {
    if (data.EntityData[i].PublishedAtDateTimeUtc != '9999-12-31T23:59:59.999') {
      associationsPublishedCount++;
    } else {
      associationsUnpublishedCount++;
    }
  }

  associationsPublished.textContent = associationsPublishedCount;
  associationsUnpublished.textContent = associationsUnpublishedCount;
});

//NOTE: Display EntitySubtype total, published, and unpublished counts
//NOTE: EntitySubtype data is currently (Jun 2025) scripted, not defined and published
const subtypes = fetchData('http://localhost:8080/entityTypes/', 'EntitySubtype', '%22Id%22%20%3E%200', '%22Ordinal%22%20ASC', 500, 1).then((data) => {
  const subtypesTotal = document.getElementById('data-total');
  subtypesTotal.textContent = data.TotalRows;

  const subtypesPublished = document.getElementById('data-published');
  const subtypesUnpublished = document.getElementById('data-unpublished');

  let subtypesPublishedCount = data.TotalRows;
  let subtypesUnpublishedCount = 0;

  subtypesPublished.textContent = subtypesPublishedCount;
  subtypesUnpublished.textContent = subtypesUnpublishedCount;
});

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

async function publishData(baseUrl, entityTypeName, method, data) {
  let responseJson = null;

  try {
    const response = await fetch(baseUrl + entityTypeName, {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:8082',
        'Access-Control-Allow-Credentials': 'true',
        'ApiKey': '6fdcf7a4-6442-4c1e-a375-9f8064ea34d0',
        'CorrelationUuid': '83b0361c-108f-4fe6-b95b-fc6f6d645e76'
      },
      credentials: 'include', //NOTE: This will include cookies with the request
      body: data
    });

    responseJson = await response.json();
    return responseJson;
  }
  catch (error) {
    responseJson = JSON.stringify({"EntityType": entityTypeName, "TotalRows": Number(-1), "EntityData": [], "Message": error.message});
    return responseJson;
  }
}
