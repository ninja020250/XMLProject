
function goDetail(model) {
    var form = document.createElement("form");
    var element1 = document.createElement("input");
    form.method = "GET";
    form.action = "ProductDetailServlet";
    element1.value = model;
    element1.name = "model";
    form.appendChild(element1);
    document.body.appendChild(form);
    form.submit();
}