// Get drop down menu element
let dropdownMenu =  document.getElementById("sprint-dropdown");
// Add new drop down menu items
for(let i = sprintList.length - 1; i >= 0; i--){
	let a = document.createElement('a');
	a.innerHTML = sprintList[i]; 
	a.className = "dropdown-item";
	a.href = "/sprintprogress?sprint=" + sprintList[i];
	dropdownMenu.appendChild(a);  
}
