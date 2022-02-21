var endless = {
    url: "../pages/main.html",
    first: true,
    proceed: true,
    page: 0,
    hasMore: true,

    init: () => {
        window.addEventListener("scroll", () => {
            if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
                endless.load();
            }
        });
        endless.load();
    },

    load: () => {
        if (endless.proceed && endless.hasMore) {
            endless.proceed = false;
            var data = new FormData(),
                nextPg = endless.page + 1;
            data.append("page", nextPg);
            fetch(endless.url, {method: "POST", body: data})
                .then(res => res.text()).then((res) => {
                if (res === "END") {
                    endless.hasMore = false;
                } else {
                    var el = document.createElement("div");
                    el.innerHTML = res;
                    document.getElementById("page-content").appendChild(el);
                    endless.proceed = true;
                    endless.page = nextPg;
                    if (endless.first) {
                        if (document.body.scrollHeight <= window.innerHeight) {
                            endless.load();
                        } else {
                            endless.first = false;
                        }
                    } else {
                        endless.first = false;
                    }
                }
            });
        }
    }
};
window.addEventListener("DOMContentLoaded", endless.init);
