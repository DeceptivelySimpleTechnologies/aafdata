//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

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

  console.log(`app.js still executing! Dispatching entityTableCreated event ...`);
  entityTableDefinitions.dispatchEvent(definitionsEvent);

  const selectedRow = entityTableDefinitions.getElementsByTagName('tr')[0]; //NOTE: Get the first row in the table

  //NOTE: Populate the attributes table with data by adding custom listener and dispatching custom event
  const attributesEvent = new CustomEvent('entityTableRowClicked', {
    detail: { selectedRow }
  });

  console.log(`app.js still executing! Dispatching entityTableRowClicked event ...`);
  entityTableAttributes.dispatchEvent(attributesEvent);

  //TODO: Add click event listener to each row in the first table??? (unless passing the EntityTable reference is sufficient)

});
