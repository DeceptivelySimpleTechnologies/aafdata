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
  // const entityTableDefinitions = document.getElementById('entitytypedefinition');
  // const entityTableAttributes = document.getElementById('entitytypeattribute');
  //
  // const selectedEntityTypeDefinitionId = document.getElementById('selectedEntityTypeDefinitionId');
  // // const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');
  //
  // const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  // const inputTextKey = document.getElementById('textkey');
  // const inputLocalizedName = document.getElementById('localizedname');
  // const inputLocalizedDescription = document.getElementById('localizeddescription');
  // const inputLocalizedAbbreviation = document.getElementById('localizedabbreviation');
  // const inputOrdinal = document.getElementById('ordinal');
  //
  // entityTableDefinitions.shadowRoot.getElementById("entitytypedefinition").querySelectorAll('tr').forEach((tableRow) => {
  //   tableRow.removeAttribute('class');
  // });
  // event.detail.row.setAttribute('class', 'selected');
  //
  // const associatedAttributes = fetchData(entityTableDefinitions.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', 'EntityTypeDefinitionEntityTypeAttributeAssociation', '%22EntityTypeDefinitionId%22%20%3D%20' + event.detail.row.querySelectorAll('td')[0].innerText, entityTableDefinitions.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 200, 1).then((data) => {
  //   let associatedAttributeIds = "";
  //
  //   //NOTE: Produce a URL-encoded, comma-separated list of EntityTypeAttribute Ids to pass as a whereClause to refresh the second EntityTable
  //   for (i = 0; i < data.EntityData.length; i++) {
  //     associatedAttributeIds = associatedAttributeIds + data.EntityData[i].EntityTypeAttributeId + '%2C';
  //   }
  //
  //   associatedAttributeIds = associatedAttributeIds.slice(0, -3);
  //   entityTableAttributes.refreshData(entityTableAttributes.getAttribute('baseUrl') || 'http://localhost:8080/entityTypeAttributes/', entityTableAttributes.getAttribute('entityTypeName') || 'EntityTypeAttribute', '%22EntityTypeAttribute%22.%22Id%22%20IN%20%28' + associatedAttributeIds + '%29', entityTableAttributes.getAttribute('sortClause') || '%22Ordinal%22%20ASC', 200, entityTableAttributes.getAttribute('pageNumber') || 1);
  // });
  //
  // selectedEntityTypeDefinitionId.value = event.detail.row.querySelectorAll('td')[0].innerText;
  //
  // inputEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[1].innerText;
  // inputTextKey.value = event.detail.row.querySelectorAll('td')[2].innerText;
  // inputLocalizedName.value = event.detail.row.querySelectorAll('td')[3].innerText;
  // inputLocalizedDescription.value = event.detail.row.querySelectorAll('td')[4].innerText;
  // inputLocalizedAbbreviation.value = event.detail.row.querySelectorAll('td')[5].innerText;
  // inputOrdinal.value = event.detail.row.querySelectorAll('td')[6].innerText;
};

function handleEntityTableAttributeRowSelected(event) {
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  const selectedEntityTypeAttributeId = document.getElementById('selectedEntityTypeAttributeId');

  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
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
  const inputOrdinal = document.getElementById('ordinal');

  entityTableAttributes.shadowRoot.getElementById("entitytypeattribute").querySelectorAll('tr').forEach((tableRow) => {
    tableRow.removeAttribute('class');
  });
  event.detail.row.setAttribute('class', 'selected');

  selectedEntityTypeAttributeId.value = event.detail.row.querySelectorAll('td')[0].innerText;

  inputEntitySubtypeId.value = event.detail.row.querySelectorAll('td')[1].innerText;
  inputTextKey.value = event.detail.row.querySelectorAll('td')[2].innerText;
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
  inputOrdinal.value = event.detail.row.querySelectorAll('td')[29].innerText;
};

function handleCreateButtonClicked(event) {
  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
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
  const inputOrdinal = document.getElementById('ordinal');

  const status = document.getElementById('status');

  const result = createData('http://localhost:8080/entityTypes/', 'EntityTypeAttribute', 'POST',
    JSON.stringify({"EntitySubtypeId": Number(inputEntitySubtypeId.value), "TextKey": inputTextKey.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "LocalizedInformation": inputLocalizedInformation.value, "LocalizedPlaceholder": inputLocalizedPlaceholder.value, "IsLocalizable": inputIsLocalizable.value, "IsToBeAssociatedWithEachEntityTypeDefinition": inputIsToBeAssociatedWithEachEntityTypeDefinition.value, "GeneralizedDataTypeEntitySubtypeId": Number(inputGeneralizedDataTypeEntitySubtypeId.value), "DataSizeOrMaximumLengthInBytesOrCharacters": Number(inputDataSizeOrMaximumLengthInBytesOrCharacters.value), "DataPrecision": Number(inputDataPrecision.value), "DataScale": Number(inputDataScale.value), "KeyTypeEntitySubtypeId": Number(inputKeyTypeEntitySubtypeId.value), "RelatedEntityTypeId": Number(inputRelatedEntityTypeId.value), "RelatedEntityTypeAttributeId": Number(inputRelatedEntityTypeAttributeId.value), "RelatedEntityTypeCardinalityEntitySubtypeId": Number(inputRelatedEntityTypeCardinalityEntitySubtypeId.value), "EntitySubtypeGroupKey": inputEntitySubtypeGroupKey.value, "ValueEntitySubtypeId": Number(inputValueEntitySubtypeId.value), "DefaultValue": inputDefaultValue.value, "MinimumValue": inputMinimumValue.value, "MaximumValue": inputMaximumValue.value, "RegExValidationPattern": inputRegExValidationPattern.value, "StepIncrementValue": Number(inputStepIncrementValue.value), "RemoteValidationMethodAsAjaxUri": "", "IndexEntitySubtypeId": Number(inputIndexEntitySubtypeId.value), "UniquenessEntitySubtypeId": Number(inputUniquenessEntitySubtypeId.value), "SensitivityEntitySubtypeId": Number(inputSensitivityEntitySubtypeId.value), "PublishedAtDateTimeUtc": "9999-12-31 23:59:59.999", "PublishedByInformationSystemUserId": Number(-1), "Ordinal": Number(inputOrdinal.value)})
  ).then((data) => {
    if (!data.hasOwnProperty("Code")) {
      status.color = "green";
      status.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' succeeded";
    }
    else {
      status.color = "red";
      status.innerText = "Attempt to create new EntityTypeDefinition as '" + inputLocalizedName.value + "' failed: " + data.Message;
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
  const inputEntitySubtypeId = document.getElementById('entitysubtypeid');
  const inputTextKey = document.getElementById('textkey');
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
  const inputOrdinal = document.getElementById('ordinal');

  const status = document.getElementById('status');

  const result = cloneData('http://localhost:8081/', 'entityTypeAttributes/', '62', 'POST',
    JSON.stringify({"TextKey": inputTextKey.value, "LocalizedName": inputLocalizedName.value, "LocalizedDescription": inputLocalizedDescription.value, "LocalizedAbbreviation": inputLocalizedAbbreviation.value, "LocalizedInformation": inputLocalizedInformation.value, "LocalizedPlaceholder": inputLocalizedPlaceholder.value, "Ordinal": Number(inputOrdinal.value)})
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

// {
//   "TextKey": "entitytypeattribute-integer-clientinformationsystemid-2b3c4",
//   "LocalizedName": "ClientInformationSystemId",
//   "LocalizedDescription": "Represents the client InformationSystem with which an ApiKey is associated, i.e. to which an ApiKey is assigned",
//   "LocalizedAbbreviation": "Cli InfoSys Id",
//   "LocalizedInformation": "Uniquely identifies this ApiKey''s client InformationSystem",
//   "LocalizedPlaceholder": "Enter InformationSystem Id",
//   "Ordinal": 1040
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
