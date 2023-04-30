document.addEventListener("DOMContentLoaded",(event)=> {

    document.getElementById("nextPageButton").addEventListener("click", () => {
        document.getElementById("currentPageID").value = document.getElementById("currentPageID").value + 1
        document.getElementById("springForm").submit()
    });

    document.getElementById("previousPageButton").addEventListener("click", () => {
        document.getElementById("currentPageID").value = document.getElementById("currentPageID").value - 1
        document.getElementById("springForm").submit()
    });

    document.getElementById("submit-filter").addEventListener("click", () => {
        event.preventDefault(); // Prevent the default form submission behavior
        let i = 0
        let j = 0
        for (const author of document.getElementsByClassName("authorLabel")) {
            if (document.getElementById("author-" + i).checked) {
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="authors[` + j + `]" id="authorId-` + j + `">`
                document.getElementById("authorId-" + j).value = document.getElementById("author-" + i + "-label").childNodes[0].textContent
                console.log(document.getElementById("author-" + i + "-label").childNodes[0].textContent)
                j++
            }
            i++
        }
        i = 0
        j = 0
        for (const author of document.getElementsByClassName("languageLabel")) {
            if (document.getElementById("language-" + i).checked) {
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="languages[` + j + `]" id="languageId-` + j + `">`
                document.getElementById("languageId-" + j).value = document.getElementById("language-" + i + "-label").childNodes[0].textContent
                console.log(document.getElementById("language-" + i + "-label").childNodes[0].textContent)
                j++
            }
            i++
        }
        i = 0
        j = 0
        for (const author of document.getElementsByClassName("physicalConditionLabel")) {
            if (document.getElementById("physicalCondition-" + i).checked) {
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="physicalConditions[` + j + `]" id="physicalConditionId-` + j + `">`
                document.getElementById("physicalConditionId-" + j).value = document.getElementById("physicalCondition-" + i + "-label").childNodes[0].textContent
                console.log(document.getElementById("physicalCondition-" + i + "-label").childNodes[0].textContent)
                j++
            }
            i++
        }
        document.getElementById("springForm").submit();
    }, true);


})