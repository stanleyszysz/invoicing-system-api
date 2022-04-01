const getData = async () => {
    const response = await fetch('http://localhost:8099/company');
    const companies = await response.json();

    for (const company in companies) {
        console.log(company)
    }

    const table = document.getElementById('company-table');

    companies.forEach(company => {
            const row = table.insertRow(-1);
            const taxIdentifier = row.insertCell(0);
            taxIdentifier.innerText = company.taxIdentifier;

            const name = row.insertCell(1);
            name.innerText = company.name;

            const city = row.insertCell(2);
            city.innerText = company.address.city;

            const postalCode = row.insertCell(3);
            postalCode.innerText = company.address.postalCode;

            const streetName = row.insertCell(4);
            streetName.innerText = company.address.streetName;

            const streetNumber = row.insertCell(5);
            streetNumber.innerText = company.address.streetNumber;

            const pensionInsurance = row.insertCell(6);
            pensionInsurance.innerText = company.pensionInsurance;

            const healthInsurance = row.insertCell(7);
            healthInsurance.innerText = company.healthInsurance;
        })
    }

function serializeFormToJson(form){
    const entries = Array.from(new FormData(form).entries())
    return JSON.stringify({
        "taxIdentifier": entries[0][1],
        "name": entries[1][1],
        "address":{
                             "city": entries[2][1],
                             "postalCode": entries[3][1],
                             "streetName": entries[4][1],
                             "streetNumber": entries[5][1]
                         },
        "pensionInsurance":entries[6][1],
        "healthInsurance":entries[7][1]
    })
}

function submitForm() {
    const form = $('#company-form');
    form.on('submit', function (e) {
        e.preventDefault();

        $.ajax({
            url: 'company',
            type: 'post',
            contentType: 'application/json',
            data: serializeFormToJson(this),
            success: function (data) {
                $("#company-table").find("tr:gt(0)").remove();
                getData()
            },
            error: function (jqXhr, textStatus, errorThrown) {
                alert(errorThrown)
            }
        });
    });
}


window.onload = function () {
    getData();
    submitForm();
}