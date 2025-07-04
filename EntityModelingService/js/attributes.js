//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//TODO: Get live authentication token
document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('entityTablePopulated', handleEntityTablePopulated);

document.addEventListener('entityTableDefinitionRowSelected', handleEntityTableDefinitionRowSelected);
document.addEventListener('entityTableAttributeRowSelected', handleEntityTableAttributeRowSelected);

document.addEventListener('createButtonClicked', handleCreateButtonClicked);
document.addEventListener('cloneButtonClicked', handleCloneButtonClicked);
document.addEventListener('associateButtonClicked', handleAssociateButtonClicked);

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
  else if (event.detail.tableId === 'entitytypeattribute') {
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

  const inputEntitySubtypeIdAssociation = document.getElementById('entitysubtypeidassociation');
  //NOTE: EntityTypeDefinitionEntityTypeAttributeAssociation has no subtype
  inputEntitySubtypeIdAssociation.value = '0';

  const inputTextKeyAssociation = document.getElementById('textkeyassociation');
  inputTextKeyAssociation.value = 'entitytypedefinitionentitytypeattributeassociation-';

  const buttonAssociate = document.getElementById('associate');
  buttonAssociate.onclick = function () {
    const associateButtonClickedEvent = new CustomEvent('associateButtonClicked', {
    });
    document.dispatchEvent(associateButtonClickedEvent);
  };

}

function handleEntityTableDefinitionRowSelected(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAssociations = document.getElementById('entitytypeassociation');

  const selectedEntityTypeDefinitionId = document.getElementById('selectedEntityTypeDefinitionId');

  const inputEntityTypeDefinitionId = document.getElementById('entityTypeDefinitionId');

  entityTableDefinitions.shadowRoot.getElementById("entitytypedefinition").querySelectorAll('tr').forEach((tableRow) => {
    tableRow.removeAttribute('class');
  });
  event.detail.row.setAttribute('class', 'selected');

  selectedEntityTypeDefinitionId.value = event.detail.row.querySelectorAll('td')[0].innerText;
  entityTableAssociations.refreshData(entityTableAssociations.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', entityTableAssociations.getAttribute('entityTypeName') || 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22EntityTypeDefinitionEntityTypeAttributeAssociation%22.%22EntityTypeDefinitionId%22%20%3D%20' + selectedEntityTypeDefinitionId.value, '%22Ordinal%22%20ASC', 10, 1);

  inputEntityTypeDefinitionId.value = selectedEntityTypeDefinitionId.value;
};

function handleEntityTableAttributeRowSelected(event) {
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');

  const inputEntitySubtypeIdAttribute = document.getElementById('entitysubtypeidattribute');
  const inputTextKeyAttribute = document.getElementById('textkeyattribute');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputLocalizedInformation = document.getElementById('localizedinformation');
  const inputLocalizedPlaceholder = document.getElementById('localizedplaceholder');
  const inputIsLocalizable = document.getElementById('islocalizable');
  const inputIsToBeAssociatedWithEachEntityTypeDefinition = document.getElementById('istobeassociatedwitheachentitytypedefinition');
  const inputGeneralizedDataTypeEntitySubtypeId = document.getElementById('generalizeddatatypeentitysubtypeid');
  const inputDataSizeOrMaximumLengthInBytesOrCharacters = document.getElementById('datasizeormaximumlengthinbytesorcharacters');
  const inputDataPrecision = document.getElementById('dataprecision');
  const inputDataScale = document.getElementById('datascale');
  const inputKeyTypeEntitySubtypeId = document.getElementById('keytypeentitysubtypeid');
  const inputRelatedEntityTypeId = document.getElementById('relatedentitytypeid');
  const inputRelatedEntityTypeAttributeId = document.getElementById('relatedentitytypeattributeid');
  const inputRelatedEntityTypeCardinalityEntitySubtypeId = document.getElementById('relatedentitytypecardinalityentitysubtypeid');
  const inputEntitySubtypeGroupKey = document.getElementById('entitysubtypegroupkey');
  const inputValueEntitySubtypeId = document.getElementById('valueentitysubtypeid');
  const inputDefaultValue = document.getElementById('defaultvalue');
  const inputMinimumValue = document.getElementById('minimumvalue');
  const inputMaximumValue = document.getElementById('maximumvalue');
  const inputRegExValidationPattern = document.getElementById('regexvalidationpattern');
  const inputStepIncrementValue = document.getElementById('stepincrementvalue');
  // const inputRemoteValidationMethodAsAjaxUri = document.getElementById('remotevalidationmethodasajaxuri');
  const inputIndexEntitySubtypeId = document.getElementById('indexentitysubtypeid');
  const inputUniquenessEntitySubtypeId = document.getElementById('uniquenessentitysubtypeid');
  const inputSensitivityEntitySubtypeId = document.getElementById('sensitivityentitysubtypeid');
  const inputOrdinalAttribute = document.getElementById('ordinalAttribute');

  const inputEntityTypeAttributeId = document.getElementById('entityTypeAttributeId');

  entityTableAttributes.shadowRoot.getElementById("entitytypeattribute").querySelectorAll('tr').forEach((tableRow) => {
    tableRow.removeAttribute('class');
  });
  event.detail.row.setAttribute('class', 'selected');

  selectedEntityTypeAttributeId.value = event.detail.row.querySelectorAll('td')[0].innerText;

  inputEntitySubtypeIdAttribute.value = event.detail.row.querySelectorAll('td')[1].innerText;
  inputTextKeyAttribute.value = event.detail.row.querySelectorAll('td')[2].innerText;
  inputLocalizedName.value = event.detail.row.querySelectorAll('td')[3].innerText;
  inputLocalizedDescription.value = event.detail.row.querySelectorAll('td')[4].innerText;
  inputLocalizedAbbreviation.value = event.detail.row.querySelectorAll('td')[5].innerText;
  inputLocalizedInformation.value = event.detail.row.querySelectorAll('td')[6].innerText;
  inputLocalizedPlaceholder.value = event.detail.row.querySelectorAll('td')[7].innerText;
  inputIsLocalizable.value = event.detail.row.querySelectorAll('td')[8].innerText;
  inputIsToBeAssociatedWithEachEntityTypeDefinition.value = event.detail.row.querySelectorAll('td')[9].innerText;
  inputGeneralizedDataTypeEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[10].innerText;
  inputDataSizeOrMaximumLengthInBytesOrCharacters.value = event.detail.row.querySelectorAll('td')[11].innerText;
  inputDataPrecision.value = event.detail.row.querySelectorAll('td')[12].innerText;
  inputDataScale.value = event.detail.row.querySelectorAll('td')[13].innerText;
  inputKeyTypeEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[14].innerText;
  inputRelatedEntityTypeId.value = event.detail.row.querySelectorAll('td')[15].innerText;
  inputRelatedEntityTypeAttributeId.value = event.detail.row.querySelectorAll('td')[16].innerText;
  inputRelatedEntityTypeCardinalityEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[17].innerText;
  inputEntitySubtypeGroupKey.value = event.detail.row.querySelectorAll('td')[18].innerText;
  inputValueEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[19].innerText;
  inputDefaultValue.value = event.detail.row.querySelectorAll('td')[20].innerText;
  inputMinimumValue.value = event.detail.row.querySelectorAll('td')[21].innerText;
  inputMaximumValue.value = event.detail.row.querySelectorAll('td')[22].innerText;
  inputRegExValidationPattern.value = event.detail.row.querySelectorAll('td')[23].innerText;
  inputStepIncrementValue.value = event.detail.row.querySelectorAll('td')[24].innerText;
  // inputRemoteValidationMethodAsAjaxUri.value = event.detail.row.querySelectorAll('td')[25].innerText;
  inputIndexEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[26].innerText;
  inputUniquenessEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[27].innerText;
  inputSensitivityEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[28].innerText;
  inputOrdinalAttribute.value = event.detail.row.querySelectorAll('td')[29].innerText;

  inputEntityTypeAttributeId.value = selectedEntityTypeAttributeId.value;
};

function handleCreateButtonClicked(event) {
  const inputEntitySubtypeIdAttribute = document.getElementById('entitysubtypeidattribute');
  const inputTextKeyAttribute = document.getElementById('textkeyattribute');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputLocalizedInformation = document.getElementById('localizedinformation');
  const inputLocalizedPlaceholder = document.getElementById('localizedplaceholder');
  const inputIsLocalizable = document.getElementById('islocalizable');
  const inputIsToBeAssociatedWithEachEntityTypeDefinition = document.getElementById('istobeassociatedwitheachentitytypedefinition');
  const inputGeneralizedDataTypeEntitySubtypeId = document.getElementById('generalizeddatatypeentitysubtypeid');
  const inputDataSizeOrMaximumLengthInBytesOrCharacters = document.getElementById('datasizeormaximumlengthinbytesorcharacters');
  const inputDataPrecision = document.getElementById('dataprecision');
  const inputDataScale = document.getElementById('datascale');
  const inputKeyTypeEntitySubtypeId = document.getElementById('keytypeentitysubtypeid');
  const inputRelatedEntityTypeId = document.getElementById('relatedentitytypeid');
  const inputRelatedEntityTypeAttributeId = document.getElementById('relatedentitytypeattributeid');
  const inputRelatedEntityTypeCardinalityEntitySubtypeId = document.getElementById('relatedentitytypecardinalityentitysubtypeid');
  const inputEntitySubtypeGroupKey = document.getElementById('entitysubtypegroupkey');
  const inputValueEntitySubtypeId = document.getElementById('valueentitysubtypeid');
  const inputDefaultValue = document.getElementById('defaultvalue');
  const inputMinimumValue = document.getElementById('minimumvalue');
  const inputMaximumValue = document.getElementById('maximumvalue');
  const inputRegExValidationPattern = document.getElementById('regexvalidationpattern');
  const inputStepIncrementValue = document.getElementById('stepincrementvalue');
  // const inputRemoteValidationMethodAsAjaxUri = document.getElementById('remotevalidationmethodasajaxuri');
  const inputIndexEntitySubtypeId = document.getElementById('indexentitysubtypeid');
  const inputUniquenessEntitySubtypeId = document.getElementById('uniquenessentitysubtypeid');
  const inputSensitivityEntitySubtypeId = document.getElementById('sensitivityentitysubtypeid');
  const inputOrdinalAttribute = document.getElementById('ordinalAttribute');

  const statusAttribute = document.getElementById('statusAttribute');

  const result = createData('http://localhost:8081/', 'entityTypeAttributes', 'POST',
    JSON.stringify({"EntitySubtypeId": Number(inputEntitySubtypeIdAttribute.value), "TextKey": inputTextKeyAttribute.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "LocalizedInformation": inputLocalizedInformation.value, "LocalizedPlaceholder": inputLocalizedPlaceholder.value, "IsLocalizable": inputIsLocalizable.value, "IsToBeAssociatedWithEachEntityTypeDefinition": inputIsToBeAssociatedWithEachEntityTypeDefinition.value, "GeneralizedDataTypeEntitySubtypeId": Number(inputGeneralizedDataTypeEntitySubtypeId.value), "DataSizeOrMaximumLengthInBytesOrCharacters": Number(inputDataSizeOrMaximumLengthInBytesOrCharacters.value), "DataPrecision": Number(inputDataPrecision.value), "DataScale": Number(inputDataScale.value), "KeyTypeEntitySubtypeId": Number(inputKeyTypeEntitySubtypeId.value), "RelatedEntityTypeId": Number(inputRelatedEntityTypeId.value), "RelatedEntityTypeAttributeId": Number(inputRelatedEntityTypeAttributeId.value), "RelatedEntityTypeCardinalityEntitySubtypeId": Number(inputRelatedEntityTypeCardinalityEntitySubtypeId.value), "EntitySubtypeGroupKey": inputEntitySubtypeGroupKey.value, "ValueEntitySubtypeId": Number(inputValueEntitySubtypeId.value), "DefaultValue": inputDefaultValue.value, "MinimumValue": inputMinimumValue.value, "MaximumValue": inputMaximumValue.value, "RegExValidationPattern": inputRegExValidationPattern.value, "StepIncrementValue": Number(inputStepIncrementValue.value), "RemoteValidationMethodAsAjaxUri": "", "IndexEntitySubtypeId": Number(inputIndexEntitySubtypeId.value), "UniquenessEntitySubtypeId": Number(inputUniquenessEntitySubtypeId.value), "SensitivityEntitySubtypeId": Number(inputSensitivityEntitySubtypeId.value), "PublishedAtDateTimeUtc": "9999-12-31 23:59:59.999", "PublishedByInformationSystemUserId": Number(-1), "Ordinal": Number(inputOrdinalAttribute.value)})
  ).then((data) => {
    if (!data.hasOwnProperty("Code")) {
      statusAttribute.color = "green";
      statusAttribute.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' succeeded";
    }
    else {
      statusAttribute.color = "red";
      statusAttribute.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' failed: " + data.Message;
    }
  });
}

// {
//   "EntitySubtypeId": 8,
//   "TextKey": "entitytypeattribute-integer-contenttypeid",
//   "LocalizedName": "ContentTypeId",
//   "LocalizedDescription": "The unique identifier of the ContentType to which this InformationSystemPrivilege applies",
//   "LocalizedAbbreviation": "Cnt Typ Id",
//   "LocalizedInformation": "This privilege''s ContentType",
//   "LocalizedPlaceholder": "Select the ContentType id",
//   "IsLocalizable": false,
//   "IsToBeAssociatedWithEachEntityTypeDefinition": false,
//   "GeneralizedDataTypeEntitySubtypeId": 11,
//   "DataSizeOrMaximumLengthInBytesOrCharacters": 0,
//   "DataPrecision": 0,
//   "DataScale": 0,
//   "KeyTypeEntitySubtypeId": -1,
//   "RelatedEntityTypeId": 0,
//   "RelatedEntityTypeAttributeId": 0,
//   "RelatedEntityTypeCardinalityEntitySubtypeId": -1,
//   "EntitySubtypeGroupKey": "",
//   "ValueEntitySubtypeId": -1,
//   "DefaultValue": "",
//   "MinimumValue": "",
//   "MaximumValue": "",
//   "RegExValidationPattern": "^[0-9-]",
//   "StepIncrementValue": 0.00,
//   "RemoteValidationMethodAsAjaxUri": "",
//   "IndexEntitySubtypeId": -1,
//   "UniquenessEntitySubtypeId": -1,
//   "SensitivityEntitySubtypeId": -1,
//   "PublishedAtDateTimeUtc": "9999-12-31 23:59:59.999",
//   "PublishedByInformationSystemUserId": -1,
//   "Ordinal": 1025,
//   "IsActive": true
// }

function handleCloneButtonClicked(event) {
  const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');

  // const inputEntitySubtypeIdAttribute = document.getElementById('entitysubtypeidattribute');
  const inputTextKeyAttribute = document.getElementById('textkeyattribute');
  const inputLocalizedName = document.getElementById('localizedname');
  const inputLocalizedDescription = document.getElementById('localizeddescription');
  const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  const inputLocalizedInformation = document.getElementById('localizedinformation');
  const inputLocalizedPlaceholder = document.getElementById('localizedplaceholder');
  const inputOrdinalAttribute = document.getElementById('ordinalAttribute');

  const statusAttribute = document.getElementById('statusAttribute');

  const result = cloneData('http://localhost:8081/', 'entityTypeAttributes/', selectedEntityTypeAttributeId.value, 'POST',
    JSON.stringify({"TextKey": inputTextKeyAttribute.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "LocalizedInformation": inputLocalizedInformation.value, "LocalizedPlaceholder": inputLocalizedPlaceholder.value, "Ordinal": Number(inputOrdinalAttribute.value)})
    ).then((data) => {
      if (!data.hasOwnProperty("Code")) {
        statusAttribute.color = "green";
        statusAttribute.innerText = "Attempt to clone existing EntityTypeDefinition as '" + inputLocalizedName.value + "' succeeded";
      }
      else {
        statusAttribute.color = "red";
        statusAttribute.innerText = "Attempt to clone existing EntityTypeDefinition as '" + inputLocalizedName.value + "' failed: " + data.Message;
      }
  });
}

// {
//   "TextKey": "entitytypeattribute-integer-clientinformationsystemid-2b3c4",
//   "LocalizedName": "ClientInformationSystemId",
//   "LocalizedDescription": "Represents the client InformationSystem with which an ApiKey is associated, i.e. to which an ApiKey is assigned",
//   "LocalizedAbbreviation": "Cli InfoSys Id",
//   "LocalizedInformation": "Uniquely identifies this ApiKey''s client InformationSystem",
//   "LocalizedPlaceholder": "Enter InformationSystem Id",
//   "Ordinal": 1040
// }

function handleAssociateButtonClicked(event) {
  const inputEntitySubtypeIdAssociation = document.getElementById('entitysubtypeidassociation');
  const inputTextKeyAssociation = document.getElementById('textkeyassociation');
  const inputEntityTypeDefinitionId = document.getElementById('entityTypeDefinitionId');
  const inputEntityTypeAttributeId = document.getElementById('entityTypeAttributeId');
  const inputOrdinalAssociation = document.getElementById('ordinalAssociation');

  const statusAssociation = document.getElementById('statusAssociation');

  const result = associateData('http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', 'POST',
    JSON.stringify({"EntitySubtypeId": Number(inputEntitySubtypeIdAssociation.value), "TextKey": inputTextKeyAssociation.value, "EntityTypeDefinitionId": Number(inputEntityTypeDefinitionId.value), "EntityTypeAttributeId": Number(inputEntityTypeAttributeId.value), "PublishedAtDateTimeUtc": "9999-12-31 23:59:59.999", "PublishedByInformationSystemUserId": Number(-1), "Ordinal": Number(inputOrdinalAssociation.value)})
  ).then((data) => {
    if (!data.hasOwnProperty("Code")) {
      statusAssociation.color = "green";
      statusAssociation.innerText = "Attempt to associate EntityTypeAttribute with EntityTypeDefinition succeeded";
    }
    else {
      statusAssociation.color = "red";
      statusAssociation.innerText = "Attempt to associate EntityTypeAttribute with EntityTypeDefinition failed: " + data.Message;
    }
  });
}

// {
//   "EntitySubtypeId": 0,
//   "TextKey": "entitytypedefinitionentitytypeattributeassociation-informationsystemprivilege-clientinformationsystemid-3c4d5",
//   "EntityTypeDefinitionId": 21,
//   "EntityTypeAttributeId": 88,
//   "Ordinal": 400
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

async function associateData(baseUrl, entityTypeName, method, data) {
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


