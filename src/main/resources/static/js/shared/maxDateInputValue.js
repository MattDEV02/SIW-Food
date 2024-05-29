const today = new Date().toISOString().split("T")[0];
let birthDateInput = document.getElementById("data-nascita");
birthDateInput.max = today;
if (birthDateInput.value === undefined || birthDateInput.value === null || birthDateInput.value === "") {
   birthDateInput.value = today;
}