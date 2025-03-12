//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

class EntityTable extends HTMLElement {
  constructor() {
    super();
    const shadow = this.attachShadow({ mode: 'open' });

    console.log(`EntityTable constructor ends.`);
  }

  //NOTE: Called each time this custom element is added to the document, and the specification recommends that custom element setup be performed in this callback rather than in the constructor
  connectedCallback() {
    const link = document.createElement('link');
    link.setAttribute('rel', 'stylesheet');
    link.setAttribute('href', 'css/style.css');
    this.shadowRoot.appendChild(link);

    //TODO: Get live authentication token
    document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

    const table = document.createElement('table');

    console.log(`EntityTable connectedCallback() Explicit parent id: ${this.getAttribute('id')}`);
    table.setAttribute('id', this.getAttribute('id') + '-23456' || 'entitytypedefinition-34567')   //TODO: Generate and append a unique id
    table.setAttribute('class', 'entity-table');

    console.log(`EntityTable connectedCallback() Not fetching data yet!`);

    //TODO: Should this be put back into the constructor???
    //TODO: And should the event listener be added to the document or to the table???
    this.addEventListener(this.getAttribute('eventListener') || 'DOMContentLoaded', async () => {
      console.log('EntityTable connectedCallback() ' + this.getAttribute('eventListener') + ` event listener added.`);
      try {
        if ((this.getAttribute('eventListener') == 'entityTableCreated') || (this.getAttribute('eventListener') == 'entityTableRowClicked')) {
          const data = await fetchData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.getAttribute('pageSize') || 20, this.getAttribute('pageNumber') || 1);
          displayData(data, table, this.getAttribute('includeColumns') || ['Id', 'EntitySubtypeId', 'TextKey'], this.getAttribute('zeroWidthColumns') || []);
        }
      }
      catch (error) {
        console.error('Error fetching data:', error);
      }
    });

    this.shadowRoot.appendChild(table);
  }
}

customElements.define('entity-table', EntityTable)

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

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const responseText = await response.text();
  data = JSON.parse(responseText);
  return data;
}

function displayData(data, table, includeColumns, zeroWidthColumns) {
  const caption = data.EntityType;
  table.createCaption().textContent = caption;

  const firstItem = data.EntityData[0];
  const headerRow = table.createTHead().insertRow();
  Object.keys(firstItem).forEach(attributeName => {
    if (includeColumns.includes(attributeName)) {
      const headerCell = headerRow.insertCell();
      headerCell.textContent = attributeName;
      headerCell.width = '33%';

      if (zeroWidthColumns.includes(attributeName)) {
        headerCell.style.display = 'none';
      }
    }
  });

  const body = table.createTBody()

  data.EntityData.forEach(item => {
    const bodyRow = body.insertRow();
    Object.entries(item).forEach(([key, value]) => {
      if (includeColumns.includes(key)) {
        const bodyCell = bodyRow.insertCell();
        bodyCell.textContent = value;
        bodyCell.width = '33%';

        if (zeroWidthColumns.includes(key)) {
          bodyCell.style.display = 'none';
        }
      }
    });
  });

  const footerRow = table.createTFoot().insertRow();
  const footerCell = footerRow.insertCell();
  footerCell.colSpan = Object.keys(firstItem).length;
  footerCell.textContent = `Records: ${data.TotalRows}`;
}
