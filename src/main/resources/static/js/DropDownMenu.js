// Get drop down menu element
let dropdownMenu =  document.getElementById("sprint-dropdown");
// Add new drop down menu items
for(let i = itemList.length - 1; i >= 0; i--){
	let a = document.createElement('a');
	a.innerHTML = itemList[i]; 
	a.className = "dropdown-item";
	a.href = itemHref + itemList[i];
	dropdownMenu.appendChild(a);  
}
