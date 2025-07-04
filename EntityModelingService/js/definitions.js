//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//TODO: Get live authentication token
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('entityTablePopulated', handleEntityTablePopulated);
document.addEventListener('entityTableDefinitionRowSelected', handleEntityTableDefinitionRowSelected);
document.addEventListener('entityTableAttributeRowSelected', handleEntityTableAttributeRowSelected);

document.addEventListener('createButtonClicked', handleCreateButtonClicked);
document.addEventListener('cloneButtonClicked', handleCloneButtonClicked);

function handleEntityTablePopulated(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  if (event.detail.tableId === 'entitytypedefinition') {
    let rows = entityTableDefinitions.shadowRoot.getElementById("entitytypedefinition").getElementsByTagName("tr");

    //NOTE: Add a click event listener to each row in the EntityTable that sends the selected row
    for (let i = 0; i < rows.length; i++) {
        rows[i].addEventListener('click', function (event) {
          const rowClickedEvent = new CustomEvent('entityTableDefinitionRowSelected', {
            detail: {
              row: rows[i]
            },
          });
          document.dispatchEvent(rowClickedEvent);
        });
    }

    //NOTE: Automatically select the first EntityDefinition row in the EntityTable after it has been populated
    const selectedRow = entityTableDefinitions.shadowRoot.querySelectorAll('tbody tr')[0];
    const rowClickedEvent = new CustomEvent('entityTableDefinitionRowSelected', {
      detail: {
        row: selectedRow
      },
    });
    document.dispatchEvent(rowClickedEvent);
  }
  else {
    let rows = entityTableAttributes.shadowRoot.getElementById("entitytypeattribute").getElementsByTagName("tr");

    //NOTE: Add a click event listener to each row in the EntityTable that sends the selected row
    for (let i = 0; i < rows.length; i++) {
      rows[i].addEventListener('click', function (event) {
        const rowClickedEvent = new CustomEvent('entityTableAttributeRowSelected', {
          detail: {
            row: rows[i]
          },
        });
        document.dispatchEvent(rowClickedEvent);
      });
    }

    //NOTE: Automatically select the first EntityAttribute row in the EntityTable after it has been populated
    const selectedRow = entityTableAttributes.shadowRoot.querySelectorAll('tbody tr')[0];
    const rowClickedEvent = new CustomEvent('entityTableAttributeRowSelected', {
      detail: {
        row: selectedRow
      },
    });
    document.dispatchEvent(rowClickedEvent);
  }

  const buttonCreate = document.getElementById('create');
  buttonCreate.onclick = function () {
    const createButtonClickedEvent = new CustomEvent('createButtonClicked', {
    });
    document.dispatchEvent(createButtonClickedEvent);
  };

  const buttonClone = document.getElementById('clone');
  buttonClone.onclick = function () {
    const cloneButtonClickedEvent = new CustomEvent('cloneButtonClicked', {
    });
    document.dispatchEvent(cloneButtonClickedEvent);
  };
}

function handleEntityTableDefinitionRowSelected(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  const selectedEntityTypeDefinitionId = document.getElementById('selectedEntityTypeDefinitionId');
  // const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');

  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputOrdinal = document.getElementById('ordinal');

  entityTableDefinitions.shadowRoot.getElementById("entitytypedefinition").querySelectorAll('tr').forEach((tableRow) => {
    tableRow.removeAttribute('class');
  });
  event.detail.row.setAttribute('class', 'selected');

  const associatedAttributes = fetchData(entityTableDefinitions.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22EntityTypeDefinitionId%22%20%3D%20' + event.detail.row.querySelectorAll('td')[0].innerText, entityTableDefinitions.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 10, 1).then((data) => {
    let associatedAttributeIds = "";

    //NOTE: Produce a URL-encoded, comma-separated list of EntityTypeAttribute Ids to pass as a whereClause to refresh the second EntityTable
    for (i = 0; i < data.EntityData.length; i++) {
      associatedAttributeIds = associatedAttributeIds + data.EntityData[i].EntityTypeAttributeId + '%2C';
    }

    associatedAttributeIds = associatedAttributeIds.slice(0, -3);
    entityTableAttributes.refreshData(entityTableAttributes.getAttribute('baseUrl') || 'http://localhost:8080/entityTypeAttributes/', entityTableAttributes.getAttribute('entityTypeName') || 'EntityTypeAttribute', '%22EntityTypeAttribute%22.%22Id%22%20IN%20%28' + associatedAttributeIds + '%29', entityTableAttributes.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 10, entityTableAttributes.getAttribute('pageNumber') || 1);
  });

  selectedEntityTypeDefinitionId.value = event.detail.row.querySelectorAll('td')[0].innerText;

  inputEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[1].innerText;
  inputTextKey.value = event.detail.row.querySelectorAll('td')[2].innerText;
  inputLocalizedName.value = event.detail.row.querySelectorAll('td')[3].innerText;
  inputLocalizedDescription.value = event.detail.row.querySelectorAll('td')[4].innerText;
  inputLocalizedAbbreviation.value = event.detail.row.querySelectorAll('td')[5].innerText;
  inputOrdinal.value = event.detail.row.querySelectorAll('td')[6].innerText;
};

function handleEntityTableAttributeRowSelected(event) {
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');

  entityTableAttributes.shadowRoot.getElementById("entitytypeattribute").querySelectorAll('tr').forEach((tableRow) => {
    tableRow.removeAttribute('class');
  });
  event.detail.row.setAttribute('class', 'selected');

  selectedEntityTypeAttributeId.value = event.detail.row.querySelectorAll('td')[0].innerText;
};

function handleCreateButtonClicked(event) {
  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputOrdinal = document.getElementById('ordinal');

  const status = document.getElementById('status');

  const result = createData('http://localhost:8081/', 'entityTypeDefinitions', 'POST',
    JSON.stringify({"EntitySubtypeId": Number(inputEntitySubtypeId.value), "TextKey": inputTextKey.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "Ordinal": Number(inputOrdinal.value)})
  ).then((data) => {
    if (!data.hasOwnProperty("Code")) {
      status.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' succeeded";
    }
    else {
      status.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' failed: " + data.Message;
    }
  });
}

// NOTE: Example of a JSON object to create an EntityTypeDefinition
// {
//   "EntitySubtypeId": 1,
//   "TextKey": "entitytypedefinition-businessentity-informationsystemrole-administrator-1a2b3",
//   "LocalizedName": "InformationSystemRole",
//   "LocalizedDescription": "Represents an InformationSystemUser role (or set of privileges/rights) in an InformationSystem",
//   "LocalizedAbbreviation": "Role",
//   "Ordinal": 21
// }

function handleCloneButtonClicked(event) {
  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputOrdinal = document.getElementById('ordinal');

  const status = document.getElementById('status');

  const result = cloneData('http://localhost:8081/', 'entityTypeDefinitions/', '7', 'POST',
    JSON.stringify({"EntitySubtypeId": Number(inputEntitySubtypeId.value), "TextKey": inputTextKey.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "Ordinal": Number(inputOrdinal.value)})
    ).then((data) => {
      if (!data.hasOwnProperty("Code")) {
        status.color = "green";
        status.innerText = "Attempt to clone existing EntityTypeDefinition as '" + inputLocalizedName.value + "' succeeded";
      }
      else {
        status.color = "red";
        status.innerText = "Attempt to clone existing EntityTypeDefinition as '" + inputLocalizedName.value + "' failed: " + data.Message;
      }
  });
}

// NOTE: Example of a JSON object to clone an EntityTypeDefinition
// {
//   "EntitySubtypeId": 1,
//   "TextKey": "entitytypedefinition-businessentity-informationsystemprivilege-create-1a2b3",
//   "LocalizedName": "InformationSystemPrivilege",
//   "LocalizedDescription": "Represents an InformationSystemUser privilege (or right) in an InformationSystem",
//   "LocalizedAbbreviation": "Prvlg",
//   "Ordinal": 20
// }

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

async function createData(baseUrl, entityTypeName, method, data) {
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

async function cloneData(baseUrl, entityTypeName, entityId, method, data) {
  let responseJson = null;

  try {
    const response = await fetch(baseUrl + entityTypeName + entityId, {
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
