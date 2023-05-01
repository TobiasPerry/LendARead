document.addEventListener("DOMContentLoaded",(event)=> {

    document.getElementById("nextPageButton").addEventListener("click", () => {
        document.getElementById("currentPageID").value = parseInt(document.getElementById("currentPageID").value) + 1
        document.getElementById("springForm").submit()
    });

    document.getElementById("previousPageButton").addEventListener("click", () => {
        document.getElementById("currentPageID").value = parseInt(document.getElementById("currentPageID").value) - 1
        document.getElementById("springForm").submit()
    });

    document.getElementById("submit-filter").addEventListener("click", () => {
        event.preventDefault(); // Prevent the default form submission behavior
        let i = 0
        let j = 0
        for (const author of document.getElementsByClassName("authorLabel")) {
            if (document.getElementById("author-" + i).checked) {
                const value = document.getElementById("author-" + i + "-label").childNodes[0].textContent
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="authors[` + j + `]" id="authorId-` + j + `" value="` + value + `">`
                j++
            }
            i++
        }
        i = 0
        j = 0
        for (const language of document.getElementsByClassName("languageLabel")) {
            if (document.getElementById("language-" + i).checked) {
                const value = document.getElementById("language-" + i + "-label").childNodes[0].textContent
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="languages[` + j + `]" id="languageId-` + j + `" value="` + value +  `">`
                j++
            }
            i++
        }
        i = 0
        j = 0
        for (const physicalCondition of document.getElementsByClassName("physicalConditionLabel")) {
            const value = document.getElementById("physicalCondition-" + i + "-label").childNodes[0].textContent
            if (document.getElementById("physicalCondition-" + i).checked) {
                document.getElementById("springForm").innerHTML += `<input type ="hidden" name="physicalConditions[` + j + `]" id="physicalConditionId-` + j + `" value="` + value + `">`
                j++
            }
            i++
        }
        document.getElementById("springForm").submit();
    }, true);


})