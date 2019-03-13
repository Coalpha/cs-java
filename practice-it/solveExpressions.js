[..document.getElementsByTagName("tbody")[0].children].forEach(row => {
  const cells = row.children;
  const expression = cells[0].children[0].innerText;
  const input = cells[1].children[0];
  input.value = eval(expression);
});
