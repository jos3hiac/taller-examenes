$(document).ready(function(){
    function addBoxRecorte(elem,image){
        var Handles=['n','s','e','w','nw','ne','se','sw']
        var Dragbars=['n','s','e','w']
        var x, y,click=false

        function value(str){
            return parseInt(str.split("p")[0])
        }
        var div=document.createElement("DIV")
        $(div).css({width:"100%",height:"100%",display:"block","z-index":"320"})
        for(i=0; i<Dragbars.length; i++){
            $(div).append($(document.createElement("DIV")).addClass("ord-"+Dragbars[i]).addClass("jcrop-dragbar")
                .css({cursor:Dragbars[i]+"-resize",position:"absolute","z-index":370+i}))
        }
        for(i=0; i<Handles.length; i++){
            $(div).append($(document.createElement("DIV")).addClass("ord-"+Handles[i]).addClass("jcrop-handle")
                .css({cursor:Handles[i]+"-resize",position:"absolute",opacity:"0.5","z-index":374+i}))
        }
        var div2=document.createElement("DIV")
        $(div2).addClass("jcrop-tracker").css({position:"absolute",top:"-2px",left:"-2px",cursor:"crosshair","z-index":"290"})
        var div3=document.createElement("DIV")
        $(div3).css({position:"absolute",left:"100px",top:"50px",width:"50%",height:"50%",border:"1px #dddddd solid","z-index":"600",display:"block"})
        var div4=document.createElement("DIV")
        $(div4).css({position:"absolute",width:"100%",height:"100%","z-index":"310",overflow:"hidden"})
        var img=document.createElement("IMG")
        $(img).width($(image).width()).height($(image).height())
        $(img).attr("src",$(image).attr("src")).css({position:"absolute",opacity:"0.5",top:-value($(div3).css("top"))-1,left:-value($(div3).css("left"))-1})
        var div5=document.createElement("DIV")
        $(div5).addClass("jcrop-tracker").css({cursor:"move","z-index":"360",position:"absolute"})
        $(div5).on("mousedown",function(e){
            if(e.which==1) {
                x = e.offsetX ; y = e.offsetY
                click=true
            }
        })
        $(div5).on("mouseup",function(e){
            if(click){
                click=false
            }
        })
        $(div2).on("mouseup",function(e){
            if(click){
                click=false
                $(div2).css({cursor:"crosshair"})
            }
        })
        $(div2).on("mousemove",function(e){
            if(click){
                var left,top
                left=e.offsetX-x;top=e.offsetY-y
                $(div3).css({left:left,top:top})
                $(div2).css({cursor:"move"})
            }
        })
        $(div5).on("mousemove",function(e){
            if(click){
                var left,top
                left=value($(div3).css("left"))+e.offsetX-x;top=value($(div3).css("top"))+e.offsetY-y
                $(div3).css({left:left,top:top})
            }
        })
        $(div4).append(img)
        $(div4).append(div5)

        $(div3).append(div4)
        $(div3).append(div)

        $(elem).append(div3)
        $(elem).append(div2)
    }
    function startRecorte(img_or){
        $(img_or).css("display","none")
        if($(img_or).next("div").hasClass("jcrop-holder").length!=0)$(img_or).next("div").remove()
        var div=document.createElement("DIV")
        $(div).addClass("jcrop-holder").css({position:"relative",width:"100%",height:"100%","background-color":"black"})
        $(img_or).after(div)
        addBoxRecorte($(div)[0],img_or)
        var img=document.createElement("IMG")
        $(img).width($(img_or).width()).height($(img_or).height())
        $(img).attr("src",$(img_or).attr("src")).css({opacity:"0.7"})
        $(div).append(img)
    }
    jQuery.fn.recorte= function(){
        if($(this).is("img"))startRecorte(this)
    }
    jQuery.fn.table= function(){
        if(!$(this).is("table"))return false
        var columns=$(this).find("tr:first th").length
        var rows=$(this).find("tbody tr").length
        var order=[]
        $(this).find("tr:first th").each(function(){
            order.push("asc")
        })
        var table=[]
        $(this).find("tbody tr").each(function(){
            var row=[]
            $(this ).find("td").each(function(){
                row.push($(this).text())
            })
            table.push(row)
        })
        table.reverse()
        var tbody=$(this).find("tbody")[0]
        $(this).find("tr:first th").click(function(){
            var idx=$(this).index()
            sort(idx)
            update()
            order[idx]=order[idx]=="asc"?"desc":"asc"
            console.log(table[0][0]+" "+table[1][0]+" "+table[2][0]+" "+table[3][0])
        })
        function sort(column){
            table.sort(function(a,b) {
                if(!isNaN(parseFloat(a[column]))){
                    if ( a[ column ] > b[ column ] ) return 1
                    if ( a[ column ] < b[ column ] ) return - 1
                    return 0
                }
                if(typeof a =="string")return a.localeCompare(b)
            });
        }
        function update(){
            $(tbody).text("")
            for(var i in table){
                var row = table[i]
                var tr = document.createElement("tr")
                for(var j in row){
                    $(tr).append('<td>'+row[j]+'</td>')
                }
                $ (tbody).append(tr)
            }
        }
    }
})
