/**
 * Created by Devin on 2016/6/30.
 */
$(function(){
    var winWidth = $(window).width();
    var winHeight= $(window).height();
    $('.container').height(winHeight-45);
    $('.main').width(winWidth-240);

    $(window).resize(function(){
        var winWidth = $(window).width();
        var winHeight= $(window).height();
        $('.container').height(winHeight-45);
        $('.main').width(winWidth-240);
    });
/*����*/
    $('.rank .wrap table').eq(0).show();
    $('.rank ul li').each(function(index){
        $(this).click(function(){
            $(this).addClass('active').siblings('li').removeClass('active');
            $('.rank .wrap table').eq(index).show().siblings('table').hide();
        });
    });
/*����*/
    $('.list1 ul').eq(0).show();
    $('.daohang1 ul li').each(function(index){
        $(this).click(function(){
            $(this).addClass('first').siblings('li').removeClass('first');
            $('.list1 ul').eq(index).show().siblings('ul').hide();
        });
    });

    $('.list2 ul').eq(0).show();
    $('.daohang2 ul li').each(function(index){
        $(this).click(function(){
            $(this).addClass('first').siblings('li').removeClass('first');
            $('.list2 ul').eq(index).show().siblings('ul').hide();
        });
    });

    $('.banner>ul>li').eq(0).show();
    $('.banner .bannertip p').eq(0).show();
    var i = 0;
    var silderlen = $('.silder>div').length;

    $('.banner .bannertip .silder>div').each(function(index){
        $(this).click(function(){
            $(this).addClass('active').siblings('div').removeClass('active');
            $('.banner .bannertip p').eq(index).show().siblings('p').hide();
            $('.banner>ul>li').eq(index).show().siblings('li').hide();
            i=index;
        });

    });
    var time = setInterval(function(){
        if(i<silderlen-1){
            i++;
            $('.banner .bannertip .silder>div').eq(i).addClass('active').siblings('div').removeClass('active');
            $('.banner .bannertip p').eq(i).show().siblings('p').hide();
            $('.banner>ul>li').eq(i).show().siblings('li').hide();
        }else{
            i=0;
            $('.banner .bannertip .silder>div').eq(i).addClass('active').siblings('div').removeClass('active');
            $('.banner .bannertip p').eq(i).show().siblings('p').hide();
            $('.banner>ul>li').eq(i).show().siblings('li').hide();
        }
    },3000);
    $('.banner').hover(function(){
        clearInterval(time);
    },function(){
        time = setInterval(function(){
            if(i<silderlen-1){
                i++;
                $('.banner .bannertip .silder>div').eq(i).addClass('active').siblings('div').removeClass('active');
                $('.banner .bannertip p').eq(i).show().siblings('p').hide();
                $('.banner>ul>li').eq(i).show().siblings('li').hide();
            }else{
                i=0;
                $('.banner .bannertip .silder>div').eq(i).addClass('active').siblings('div').removeClass('active');
                $('.banner .bannertip p').eq(i).show().siblings('p').hide();
                $('.banner>ul>li').eq(i).show().siblings('li').hide();
            }
        },3000);
    });

    $('.container .side .menu>div').hover(function(){
        $(this).addClass('active').siblings('div').removeClass('active');
    },function(){
        $(this).removeClass('active');
    });
    
    /*地图*/
    if($('.zuixin .pic').length>6){
        runing('.zuixin');
    }
    if($('.zuire .pic').length>6){
        runing('.zuire');
    }
});

function runing(obj){
    var num = 0 ;
    $(obj+' .List1').append($(obj+' .List1').html());
    var imglength = $(obj+' .pic').length;
    var picwidth = $('.pic').width();
    $(obj+' .rollBox .ScrCont').width(imglength*picwidth);

    var time2 =  setInterval(function(){
        if(num<=imglength/2-1){
            num++;
            $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');

        }else{
            $(obj+' .rollBox .ScrCont').css('left','0px');

            num=1;
            $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');

        }
    },2000);
    $(obj).hover(function(){
        clearInterval(time2);
    },function(){
        time2=setInterval(function(){
            if(num<=imglength/2-1){
                num++;
                $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');

            }else{
                $(obj+' .rollBox .ScrCont').css('left','0px');
                num=1;
                $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');
            }
        },2000);
    });
    $(obj+' .img1').click(function(){
        if(num<=imglength/2-1) {
            num++;
            $(obj+' .rollBox .ScrCont').animate({left: -picwidth * num}, 'slow');
        }else{
            $(obj+' .rollBox .ScrCont').css('left','0px');
            num=1;
            $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');
        }
    })
    $(obj+' .img2').click(function(){
        if(num>0){
            num--;
            $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');
        }else{
            $(obj+' .rollBox .ScrCont').css('left',-imglength/2*picwidth);
            num=9;
            $(obj+' .rollBox .ScrCont').animate({left:-picwidth*num},'slow');
        }

    })
}

