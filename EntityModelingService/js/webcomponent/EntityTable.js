//NOTE: Copyright © 2003-2025 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

class EntityTable extends HTMLElement {

  currentPageNumber = 1;
  pageSize = 20;

  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  //NOTE: Called each time this custom element is added to the document, and the specification recommends that custom element setup be performed in this callback rather than in the constructor
  async connectedCallback() {
    const link = document.createElement('link');
    link.setAttribute('rel', 'stylesheet');
    link.setAttribute('href', 'css/style.css');
    this.shadowRoot.appendChild(link);

    //TODO: Get live authentication token
    document.cookie = "Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; path=/";

    this.currentPageNumber = this.getAttribute('pageNumber') || 1;
    this.pageSize = this.getAttribute('pageSize') || 20;

    try {
      await this.refreshData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.pageSize, this.currentPageNumber);
    //   //TODO: Check if data is empty and handle it accordingly
    }
    catch (error) {
    //   //TODO: Display error message in the table
    }

    const populatedEvent = new CustomEvent('entityTablePopulated', {
      detail: {
        tableId: this.getAttribute('id')
      },
    });

    document.dispatchEvent(populatedEvent);
  }

  async refreshData(baseUrl, entityTypeName, whereClause, sortClause, pageSize, pageNumber) {
    let table = this.shadowRoot.querySelector('table');

    if (table) {
      table.remove();
    }

    table = document.createElement('table');

    table.setAttribute('id', this.getAttribute('id') || 'entitytypedefinition-' + Math.floor(Math.random() * (99999 - 10000 + 1)) + 10000);
    table.setAttribute('class', 'entity-table');

    try {
      const data = await this.fetchData(baseUrl, entityTypeName, whereClause, sortClause, pageSize, pageNumber);
      //TODO: Check if data is empty and handle it accordingly
      // this.displayData(data, table, this.getAttribute('includeColumns') || ['Id', 'EntitySubtypeId', 'TextKey'], this.getAttribute('zeroWidthColumns') || []);
      this.displayData(data, table, this.getAttribute('columnConfiguration') || [['Id', 'Id', '0'], ['EntitySubtypeId', 'Subtype', '50'], ['TextKey', 'TextKey', '50']]);
    }
    catch (error) {
      console.error('Error fetching data:', error);
      //TODO: Display error message in the table
    }

    this.shadowRoot.appendChild(table);
    //
    // this.connectedCallback();
  }

  async fetchData(baseUrl, entityTypeName, whereClause, sortClause, pageSize, pageNumber) {
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

  displayData(data, table, columnConfiguration) {
    const caption = data.EntityType;
    table.createCaption().textContent = caption;

    const firstItem = data.EntityData[0];
    const headerRow = table.createTHead().insertRow();

    const validJsonString = columnConfiguration.replace(/'/g, '"');
    const configurationArray = JSON.parse(validJsonString);
    let elementIndex = -1;

    Object.keys(firstItem).forEach(attributeName => {
      if (configurationArray.map(item => item[0]).includes(attributeName)) {
        const headerCell = headerRow.insertCell();
        elementIndex = configurationArray.map(item => item[0]).indexOf(attributeName);
        headerCell.textContent = configurationArray[elementIndex][1];
        headerCell.style.cssText = "width: " + configurationArray[elementIndex][2] + "%";

        if (configurationArray[elementIndex][2] === '0') {
          headerCell.style.display = 'none';
        }
      }
    });

    const body = table.createTBody()

    data.EntityData.forEach(record => {
      const bodyRow = body.insertRow();

      Object.entries(record).forEach(attribute => {
        if (configurationArray.map(item => item[0]).includes(attribute[0])) {
          const bodyCell = bodyRow.insertCell();
          elementIndex = configurationArray.map(item => item[0]).indexOf(attribute[0]);
          bodyCell.textContent = attribute[1];
          bodyCell.style.cssText = "width: " + configurationArray[elementIndex][2] + "%";

          if (configurationArray[elementIndex][2] === '0') {
            bodyCell.style.display = 'none';
          }
        }
      });
    });

    const footerRow = table.createTFoot().insertRow();
    const footerCell = footerRow.insertCell();
    footerCell.colSpan = Object.keys(firstItem).length;

    let firstPageLink = document.createElement('a');
    firstPageLink.id = 'firstPageLink';
    firstPageLink.href = '#';
    firstPageLink.textContent = '<<';
    firstPageLink.onclick = function() {
      this.currentPageNumber = 1;
      this.refreshData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.pageSize, this.currentPageNumber);
      return false;
    }.bind(this)
    footerCell.appendChild(firstPageLink);

    let previousPageLink = document.createElement('a');
    previousPageLink.id = 'previousPageLink';
    previousPageLink.href = '#';
    previousPageLink.textContent = '|<';
    previousPageLink.onclick = function() {
      if (this.currentPageNumber > 1) {
        this.currentPageNumber--;
        this.refreshData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.pageSize, this.currentPageNumber);
        return false;
      }
    }.bind(this)
    footerCell.appendChild(previousPageLink);

    let currentPageNumberDisplay = document.createElement('span');
    currentPageNumberDisplay.id = 'currentPageNumber';
    currentPageNumberDisplay.textContent = `Page: ${this.currentPageNumber} `;
    footerCell.appendChild(currentPageNumberDisplay);

    let nextPageLink = document.createElement('a');
    nextPageLink.id = 'nextPageLink';
    nextPageLink.href = '#';
    nextPageLink.textContent = '>|';
    nextPageLink.onclick = function() {
      if (this.currentPageNumber < Math.ceil(data.TotalRows / this.pageSize)) {
        this.currentPageNumber++;
        this.refreshData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.pageSize, this.currentPageNumber);
        return false;
      }
    }.bind(this);
    footerCell.appendChild(nextPageLink);

    let lastPageLink = document.createElement('a');
    lastPageLink.id = 'lastPageLink';
    lastPageLink.href = '#';
    lastPageLink.textContent = '>>';
    lastPageLink.onclick = async function() {
      this.currentPageNumber = Math.ceil(data.TotalRows / this.pageSize);
      await this.refreshData(this.getAttribute('baseUrl') || 'http://localhost:8080/entityTypes/', this.getAttribute('entityTypeName') || 'EntityTypeDefinition', this.getAttribute('whereClause') || '%22Id%22%20%3E%20-2', this.getAttribute('sortClause') || '%22Ordinal%22%20ASC', this.pageSize, this.currentPageNumber);
      return false;
    }.bind(this)
    footerCell.appendChild(lastPageLink);

    let pageSizeDisplay = document.createElement('span');
    pageSizeDisplay.id = 'pageSize';
    pageSizeDisplay.textContent = `Page Size: ${this.pageSize} `;
    footerCell.appendChild(pageSizeDisplay);

    let totalRecordsDisplay = document.createElement('span');
    totalRecordsDisplay.id = 'totalRecords';
    totalRecordsDisplay.textContent = `Total Records: ${data.TotalRows} `;
    footerCell.appendChild(totalRecordsDisplay);
  }
}

customElements.define('entity-table', EntityTable)
