$(document).ready( function () {
    var tabel = loaddata();

    $("#employeesTable tbody").on('click', 'button[id="#btnUpdate"]', function(){
        var data = tabel.row($(this).parents('tr')).data();
        window.open("/student/update/"+data["npm"]);
    })


    $("#employeesTable tbody").on('click', 'button[id="#btnDelete"]', function(){
        var data = tabel.row($(this).parents('tr')).data();
        var bool = confirm("Apakah anda yakin?")
        if(bool){
            $.ajax({
                url: "/student/delete/"+data["npm"],
                success: function(e){
                    tabel.ajax.reload();
                }
            });
        }
        //alert(data["npm"]);
    })
});

function loaddata(){
    var iDisplayIndex = 0,nRow;
    var tabel = $("#employeesTable").DataTable({
        "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        } ],
        "ajax": "/student/viewData",
        "sAjaxDataProp": "",
        "columns": [
            { render: function (data, type, row, meta) {
                  return meta.row + meta.settings._iDisplayStart + 1;
              }
            },
            { data: "npm" },
            { data: "name" },
            { data: "gpa" },
            {
                data: "gpa",
                render: function(data){
                    if (data>=3.49){
                        return "Cum Laude!";
                    }else{
                        return "Sangat Memuaskan";
                    }
                }
            },
            {
                defaultContent: "<button class='btn btn-default btn-xs btn-primary' id='#btnUpdate'>Update</button>&nbsp;<button class='btn btn-danger btn-xs' id='#btnDelete'>Delete</button>"
            }
        ]
    });
    return tabel;
}