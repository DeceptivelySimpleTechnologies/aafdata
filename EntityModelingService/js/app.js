//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

//NOTE: "Wire up" the two EntityTables so that when a row is clicked in the first EntityTable, the data is sent to the second EntityTable
document.addEventListener('entityTablePopulated', handleEntityTablePopulated);

function handleEntityTablePopulated(event) {
  const entityTableDefinitions = document.getElementById('entitytypedefinition');
  const entityTableAttributes = document.getElementById('entitytypeattribute');

  if (event.detail.tableId === 'entitytypedefinition') {
    //NOTE: Get the first data row in the table, i.e. not the header row
    const selectedRow = entityTableDefinitions.shadowRoot.querySelectorAll('tbody tr')[0];
    console.log(`Selected row id: ${selectedRow.querySelectorAll('td')[0].innerText}`);
    entityTableAttributes.refreshData(entityTableAttributes.getAttribute('baseUrl') || 'http://localhost:8080/entityTypeAttributes/', entityTableAttributes.getAttribute('entityTypeName') || 'EntityTypeAttribute', '%22EntitySubtypeId%22%20%3D%2067', entityTableAttributes.getAttribute('sortClause') || '%22Ordinal%22%20ASC', entityTableAttributes.getAttribute('pageSize') || 20, entityTableAttributes.getAttribute('pageNumber') || 1)
  }
}
