$("header .nav-link").each(function (_, el) {
    const isTextWhite = $(el).hasClass("text-white");

    $(el).hover(() => {
        if (!isTextWhite)
            $(el).addClass("text-white");
    }, () => {
        if (!isTextWhite)
            $(el).removeClass("text-white");
    });
});