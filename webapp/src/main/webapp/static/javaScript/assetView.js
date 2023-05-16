document.addEventListener("DOMContentLoaded",()=> {

    document.getElementById("authorClick").addEventListener("click", () => {
        const author = document.getElementById("authorClick").dataset.author;
        document.getElementById("formSearch").innerHTML += `<input type ="hidden" name="search" value="` + author + `">`
        document.getElementById("formSearch").submit();
    })

    document.getElementById("languageClick").addEventListener("click", () => {
        const language = document.getElementById("languageClick").dataset.language;
        document.getElementById("formSearch").innerHTML += `<input type ="hidden" name="languages[0]" value="` + language + `">`;
        document.getElementById("formSearch").submit();
    })

    document.getElementById("physicalConditionClick").addEventListener("click", () => {
        const physicalCondition = document.getElementById("physicalConditionClick").dataset.physicalcondition;
        document.getElementById("formSearch").innerHTML += `<input type ="hidden" name="physicalConditions[0]" value="` + physicalCondition + `">`;
        document.getElementById("formSearch").submit();
    })

});