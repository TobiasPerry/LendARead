const dates = [...document.querySelectorAll('td.date-column')];

dates.forEach((date) => {
    if (date.dataset.assetStatus === 'REJECTED' || date.dataset.assetStatus === 'PRIVATE') {
        return;
    }
    const today = new Date()
    let diff = new Date(date.textContent) - today
    diff = Math.ceil(diff / (1000 * 3600 * 24))
    if (diff > 3) return;
    let color = 'gold'
    let message = warningFewDays.replace('%d', diff.toString());
    if (diff < 2) {
        color = 'red'
    }
    if (diff < 0) {
        message = warningReturnDate;
    }
    date.appendChild(getWarningIcon(color, message))
})

function getWarningIcon(color, message) {
    // Create an SVG element
    let svgElement = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    svgElement.setAttribute('xmlns', 'http://www.w3.org/2000/svg');
    svgElement.setAttribute('width', '16');
    svgElement.setAttribute('height', '16');
    svgElement.setAttribute('fill', color); // Set the fill color to red
    svgElement.setAttribute('class', 'bi bi-exclamation-triangle-fill');
    svgElement.setAttribute('viewBox', '0 0 16 16');


    // Create the <path> element and set its 'd' attribute
    let pathElement = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    pathElement.setAttribute('d', 'M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z');

    // Append the <path> element to the <svg> element
    svgElement.appendChild(pathElement);

    let container = document.createElement('a')
    container.classList.add('tooltip-container')
    container.appendChild(svgElement)
    let tooltip = document.createElement('div')
    tooltip.classList.add("tooltip")
    tooltip.textContent = message
    container.appendChild(tooltip)
    return container;
}