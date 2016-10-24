/**
 * Created by wangzhongfu on 2016/7/25.
 */
(function () {
    var c = ["cmCreatePageviewTag", "cmCreateProductviewTag", "cmCreateShopAction5Tag", "cmDisplayShops", "cmCreateShopAction9Tag", "cmCreateOrderTag", "cmCreateRegistrationTag", "cmCreateElementTag", "cmCreateConversionEventTag", "cmCreateManualPageviewTag", "cmCreateManualLinkClickTag", "cmCreateManualImpressionTag", "cmCreateCustomTag", "cmSetupOther", "cmSetCurrencyCode", "cmDisplayShop9s", "cmDisplayShop5s"], b = [];
    (function g() {
        if (!e()) {
            for (var h = 0; h < c.length; h++) {
                d(c[h])
            }
            a()
        }
    })();
    function e() {
        for (var h = 0;
             h < c.length; h++) {
            if (typeof(window[c[h]]) !== "function" || window[c[h]].isGhost) {
                return false
            }
        }
        return true
    }

    function d(h) {
        window[h] = function () {
            b.push({functionName: h, args: arguments})
        };
        window[h].isGhost = true
    }

    function a() {
        setTimeout(function () {
            if (e()) {
                f()
            } else {
                a()
            }
        }, 50)
    }

    function f() {
        for (var h = 0; h < b.length; h++) {
            window[b[h].functionName].apply(this, b[h].args)
        }
    }
})();
var v16elu = {
    CID: "51040000",
    cmSetClientID: {
        id: null,
        managedFirstParty: true,
        dataCollectionDomain: "data.coremetrics.com",
        cookieDomain: document.domain.split(".").splice(-2, 2).join(".")
    },
    NTPT_DOWNLOADTYPES: "bqy,doc,dot,exe,flv,jpg,png,mov,mp3,pdf,pps,ppt,rss,sh,swf,tar,txt,wmv,xls,xml,zip,avi,eps,gif,lwp,mas,mp4,pot,prz,rtf,wav,wma,123,odt,ott,sxw,stw,docx,odp,otp,sxi,sti,pptx,ods,ots,sxc,stc,xlsx",
    NTPT_DOMAINLIST: ".ibm.co,.ibm.com,.lotuslive.com,.cognos.com,.webdialogs.com,.servicemanagementcenter.com,.xtify.com,.ibmcloud.com,.ibmdw.net,.bluemix.net,.smartercitiescloud.com",
    evhndlr: true,
    bind_event: function (b, a) {
        if ((typeof(a) == "function") && document.body) {
            if (document.body.addEventListener) {
                document.body.addEventListener(b, a, true)
            } else {
                if (document.body.attachEvent) {
                    document.body.attachEvent("on" + b, a)
                }
            }
        }
    },
    event_tracking: function () {
        var a = "click";
        this.bind_event(a, this.download_offsite_tracking);
        a = "mouseup";
        this.bind_event(a, this.middle_click_tracking);
        a = (navigator.appVersion.indexOf("Chrome") || navigator.appVersion.indexOf("MSIE") != -1) ? "contextmenu" : "mousedown";
        this.bind_event(a, this.right_click_tracking)
    },
    Querystring: function (a) {
        this.params = {};
        if (a == null) {
            a = location.search.substring(1, location.search.length)
        }
        if (a.length == 0) {
            return
        }
        a = a.replace(/\+/g, " ");
        var c = a.split("&"), f = "", b = "", e = "";
        for (var d = 0; d < c.length; d++) {
            f = c[d].split("=");
            b = decodeURIComponent(f[0]);
            e = (f.length == 2) ? decodeURIComponent(f[1]) : b;
            this.params[b] = e
        }
    },
    evt_element: function (b, a) {
        var d = b.target || b.srcElement;
        while (d.tagName && (d.tagName.toLowerCase() != a.toLowerCase())) {
            var c = d.parentElement || d.parentNode;
            if (!c) {
                return d
            }
            d = c
        }
        return d
    },
    download_offsite_tracking: function (a) {
        a = a || (window.event || "");
        if (a && ((typeof(a.which) != "number") || (a.which == 1))) {
            var c = a.target || a.srcElement, b = (c.getAttribute("onclick") !== null) ? (c.getAttribute("onclick").indexOf("ibmStats.event")) : -1;
            if (b === -1) {
                v16elu.tracking_events(a)
            }
        }
    },
    right_click_tracking: function (a) {
        a = a || (window.event || "");
        if (a) {
            var b = a.which || a.button;
            if ((b != 1) || (navigator.userAgent.indexOf("Safari") != -1)) {
                v16elu.tracking_events(a)
            }
        }
    },
    middle_click_tracking: function (a) {
        a = a || (window.event || "");
        if (a && ((typeof(a.which) !== "undefined" && a.which == 2) || (typeof(a.button) !== "undefined" && a.button == 4))) {
            v16elu.tracking_events(a)
        }
    },
    tracking_events: function (n) {
        var b = "";
        if (typeof(window.digitalData) != "undefined" && typeof(window.digitalData.page) != "undefined") {
            if (typeof(window.digitalData.page.pageInfo) != "undefined" && typeof(window.digitalData.page.pageInfo.pageID) != "undefined") {
                b = window.digitalData.page.pageInfo.pageID
            } else {
                if (typeof(window.digitalData.page.pageID) != "undefined") {
                    b = window.digitalData.page.pageID
                }
            }
            var c = new Date(), d = window.location.href.replace(/-_-/g, "---");
            b = b + "-_--_--_--_--_--_-" + window.pageViewAttributes.split("-_-")[17] + "-_-" + d + "-_-" + c.getTime() + "-_-" + window.pageViewAttributes.split("-_-")[0]
        }
        if (typeof(v16elu.siteID) !== "undefined" && (v16elu.siteID.toLowerCase() == "e065" || v16elu.siteID.toLowerCase() == "e020" || v16elu.siteID.toLowerCase() == "e021")) {
            var s = (typeof(window.w3SSIParams.htmlfid) !== "undefined") ? window.w3SSIParams.htmlfid : "undefined";
            b = b + "-_-" + s
        }
        if (typeof(v16elu.siteID) !== "undefined" && (v16elu.siteID.toLowerCase() == "odw" || v16elu.siteID.toLowerCase() == "w3odw")) {
            if (typeof(window.IBMPageCategory) !== "undefined") {
                b = b + "-_-" + window.IBMPageCategory
            }
        }
        var z = v16elu.evt_element(n, "A");
        if ((typeof z.tagName !== "undefined" && z.tagName.toLowerCase() == "a") && !!z.href) {
            var a = z.hostname ? (z.hostname.split(":")[0]) : "", q = z.protocol || "", w = escape(z.href), t = z.search ? z.search.substring(z.search.indexOf("?") + 1, z.search.length) : "", m = (z.protocol == "ftp:") ? w.substr(8) : ((z.protocol == "https:") ? w.substr(10) : w.substr(9)), A = (z.protocol == "ftp:") ? z.href.substr(6) : ((z.protocol == "https:") ? z.href.substr(8) : z.href.substr(7)), A = decodeURIComponent(A);
            m = decodeURIComponent(m);
            evLinkTitle = (navigator.appVersion.indexOf("MSIE") != -1) ? z.innerText : z.textContent, qs1 = new v16elu.Querystring(t), v1 = qs1.get("attachment"), v2 = qs1.get("FILE"), v3 = qs1.get("attachmentName");
            if (m.indexOf("-_-") != -1) {
                m = m.replace(/-_-/g, "---")
            }
            var r = "none";
            if (v1 != null) {
                r = v1
            } else {
                if (v2 != null) {
                    r = v2
                } else {
                    if (v3 != null) {
                        r = v3
                    }
                }
            }
            var y = r.toLowerCase(), g = z.pathname.toLowerCase();
            if (A.length > 50) {
                A = A.substring(0, 22) + "..." + A.substring(A.length - 25, A.length)
            }
            var o = evLinkTitle + "-_-null-_-null-_-null-_-" + m.toLowerCase() + "-_-" + evLinkTitle + "-_-null-_--_--_-" + b;
            if (typeof(v16elu.siteID) !== "undefined" && (v16elu.siteID.toLowerCase() == "p023")) {
                var k = "undefined", v = "undefined";
                if (window.location.hash !== "") {
                    var j = window.location.hash.split("&");
                    for (var u = 0; u < j.length; u++) {
                        if (j[u].indexOf("query") !== -1) {
                            v = j[u].substring(7, j[u].length)
                        }
                    }
                }
                var p = document.querySelector("input#ibm-w3search-keyword").value;
                if (v !== p) {
                    v = p
                }
                if (z.className.trim() == "sm-result-link" || z.className.indexOf("sm") !== -1) {
                    k = "SEARCH:W3R1:Redirects:SM"
                } else {
                    if (z.className.trim() == "result-link" || z.className.substring(0, 12) == "result-link ") {
                        k = "SEARCH:W3R1:Redirects"
                    }
                }
                o = evLinkTitle + "-_-" + decodeURIComponent(v) + "-_-" + k + "-_-null-_-" + m.toLowerCase() + "-_-" + evLinkTitle + "-_-null-_--_--_-" + b
            }
            if (v16elu.domtest(a)) {
                if (v16elu.match(g, v16elu.NTPT_DOWNLOADTYPES) || v16elu.match(y, v16elu.NTPT_DOWNLOADTYPES)) {
                    var f = z.pathname ? ((z.pathname.indexOf("/") != 0) ? "/" + z.pathname : z.pathname) : "/", h = "", l = document.all ? z.innerText : z.text, B = v16elu.evt_element(n, "IMG");
                    if (B.alt) {
                        h = B.alt
                    } else {
                        if (l) {
                            h = l
                        } else {
                            if (z.innerHTML) {
                                h = z.innerHTML
                            }
                        }
                    }
                    if (v16elu.evhndlr != false) {
                        if (r == "none") {
                            coremetricsParam = m.toLowerCase() + "-_-" + o;
                            if (typeof cmCreateElementTag !== "undefined") {
                                cmCreateElementTag(A.toLowerCase(), "download", "download-_-" + coremetricsParam)
                            }
                        } else {
                            coremetricsParam = y + "-_-" + o;
                            if (typeof cmCreateElementTag !== "undefined") {
                                cmCreateElementTag(y, "download", "download-_-" + coremetricsParam)
                            }
                        }
                    }
                } else {
                    if (typeof cmCreateElementTag !== "undefined") {
                        cmCreateElementTag(A.toLowerCase(), "page click", "page click-_-" + m + "-_-" + o)
                    }
                }
            } else {
                if ((a.length > 0) && (q.indexOf("http") == 0 || q.indexOf("mailto") == 0) && (!v16elu.domtest(a))) {
                    if (v16elu.evhndlr != false) {
                        if (typeof cmCreateElementTag !== "undefined") {
                            cmCreateElementTag(A.toLowerCase(), "external link", "external link-_-" + m + "-_-" + o)
                        }
                    }
                }
            }
        }
    },
    domtest: function (d) {
        var b = false;
        if (d.length > 0) {
            d = d.toLowerCase();
            if (d == window.location.hostname.toLowerCase()) {
                b = true
            } else {
                var e = this.varlist(v16elu.NTPT_DOMAINLIST);
                var a = e.length;
                for (var c = 0; c < a; c++) {
                    if (d == e[c] || d.search(e[c]) != -1) {
                        b = true
                    }
                }
            }
        }
        return b
    },
    varlist: function (d) {
        var b = d.toLowerCase().split(","), a = b.length;
        for (var c = 0; c < a; c++) {
            b[c] = b[c].replace(/^\s*/, "").replace(/\s*$/, "")
        }
        return b
    },
    match: function (f, e) {
        var d = f.substring(f.lastIndexOf(".") + 1, f.length), c = this.varlist(e), b = c.length;
        for (var a = 0; a < b; a++) {
            if (d == c[a]) {
                return true
            }
        }
        return false
    },
    pause: function (c) {
        var b = new Date();
        var a = null;
        do {
            a = new Date()
        } while (a - b < c)
    },
    get_meta_tag: function (a) {
        var d = document.getElementsByTagName("meta"), c = null;
        for (var b = 0; b < d.length; b++) {
            if (d[b].getAttribute("name") == a) {
                c = d[b].getAttribute("content")
            }
        }
        if (c == null) {
            c = v16elu.checkJSON(a)
        }
        return c
    },
    checkJSON: function (b) {
        b = b.toLowerCase().replace(".", "_");
        if (digitalData.page) {
            for (var a in digitalData.page.attributes) {
                if (digitalData.page.attributes.hasOwnProperty(a) && a.toLowerCase() == b) {
                    return digitalData.page.attributes[a]
                }
            }
        }
        return null
    },
    create_QueryString: function () {
        window.QueryString = {};
        var c = window.location.search.substring(1), d = c.split("&");
        for (var b = 0; b < d.length; b++) {
            var e = d[b].split("=");
            if (typeof QueryString[e[0]] === "undefined") {
                QueryString[e[0]] = e[1]
            } else {
                if (typeof QueryString[e[0]] === "string") {
                    var a = [QueryString[e[0]], e[1]];
                    QueryString[e[0]] = a
                } else {
                    QueryString[e[0]].push(e[1])
                }
            }
        }
    },
    w3_event_creation: function (a) {
        if (typeof(v16elu.siteID) !== "undefined" && v16elu.siteID.toLowerCase() == "p023") {
            v16elu.event_conversion(a)
        }
    },
    event_conversion: function (b) {
        if (!b.ibmEV) {
            b.ibmEV = "null"
        }
        if (!b.ibmEvAction) {
            b.ibmEvAction = "null"
        }
        if (!b.ibmEvGroup) {
            b.ibmEvGroup = "null"
        }
        if (!b.ibmEvName) {
            b.ibmEvName = "null"
        }
        if (!b.ibmEvModule) {
            b.ibmEvModule = "null"
        }
        if (!b.ibmEvSection) {
            b.ibmEvSection = "null"
        }
        if (!b.ibmEvTarget) {
            b.ibmEvTarget = "null"
        }
        if (!b.ibmEvFileSize) {
            b.ibmEvFileSize = "null"
        }
        if (!b.ibmEvLinkTitle) {
            b.ibmEvLinkTitle = "null"
        }
        var a = b.ibmEV + "-_-" + b.ibmEvAction + "-_-" + b.ibmEvName + "-_-" + b.ibmEvGroup + "-_-" + b.ibmEvModule + "-_-" + b.ibmEvSection + "-_-" + b.ibmEvTarget + "-_-" + b.ibmEvLinkTitle + "-_-" + b.ibmEvFileSize;
        a = decodeURIComponent(a);
        v16elu.create_cmElement(b, b.ibmEV, b.ibmEvAction, a)
    },
    create_cmElement: function (m, w, e, f) {
        var a = "";
        if (typeof(window.digitalData) != "undefined" && typeof(window.digitalData.page) != "undefined") {
            if (typeof(window.digitalData.page.pageInfo) != "undefined" && typeof(window.digitalData.page.pageInfo.pageID) != "undefined") {
                a = window.digitalData.page.pageInfo.pageID
            } else {
                if (typeof(window.digitalData.page.pageID) != "undefined") {
                    a = window.digitalData.page.pageID
                }
            }
        }
        if (a === "") {
            var t = document.location.pathname;
            var k = t.substring(t.lastIndexOf("/") + 1, t.length);
            var n = ["index.php", "index.phtml", "index.shtml", "index.wss", "index.jsp", "index.jspa", "index.htm", "index.html", "index"];
            for (var s = 0; s < n.length; s++) {
                if (n[s] == k.toLowerCase()) {
                    t = t.replace(k, "")
                }
            }
            if (t.indexOf("iwm") !== -1) {
                var g = document.location.href.substring(document.location.href.indexOf("?") + 1), v, u, s, p, v = g.split("&");
                for (s = 0, p = v.length; s < p; s++) {
                    u = v[s].split("=");
                    if (u[0] == "source") {
                        t += "?source=" + u[1]
                    }
                }
            }
            t = t.replace(/[(\/)(?)(#)]+$/, "");
            a = document.location.host + t
        }
        w = decodeURIComponent(w);
        e = decodeURIComponent(e);
        if (w.length > 50) {
            w = w.substring(0, 22) + "..." + w.substring(w.length - 25, w.length)
        }
        if (e.length > 50) {
            e = e.substring(0, 22) + "..." + e.substring(e.length - 25, e.length)
        }
        var b = new Date(), d = window.location.href.replace(/-_-/g, "---"), c = (m.ibmConversion && m.ibmConversion == "true") ? "-_--_--_--_--_--_-" : ("-_-" + m.ibmEvVidStatus + "-_-" + m.ibmEvVidTimeStamp + "-_-" + m.ibmEvVidLength + "-_--_--_-");
        if (typeof(window.pageViewAttributes) != "undefined") {
            a = a + c + window.pageViewAttributes.split("-_-")[17] + "-_-" + d + "-_-" + b.getTime() + "-_-" + window.pageViewAttributes.split("-_-")[0]
        } else {
            window.NTPT_IBMer = (String(document.cookie).match(/(^| )(w3ibmProfile|w3_sauid|PD-W3-SSO-|OSCw3Session|IBM_W3SSO_ACCESS)=/)) ? 1 : 0;
            a = a + c + window.loadingTime + "-_-" + d + "-_-" + window.loadingTime + "-_-" + window.NTPT_IBMer
        }
        if (typeof(m.ibmConversion) == "undefined" && typeof(v16elu.siteID) !== "undefined" && (v16elu.siteID.toLowerCase() == "odw" || v16elu.siteID.toLowerCase() == "w3odw")) {
            if (String(v16elu.get_meta_tag("IBM.WTMCategory")) !== "null") {
                a = a + "-_-" + String(v16elu.get_meta_tag("IBM.WTMCategory"))
            } else {
                if (typeof(window.digitalData.page.category) !== "undefined" && typeof(window.digitalData.page.category.primaryCategory) !== "undefined") {
                    a = a + "-_-" + window.digitalData.page.category.primaryCategory
                }
            }
        }
        if (typeof(m.ibmConversion) == "undefined" && typeof(v16elu.siteID) !== "undefined" && (v16elu.siteID.toLowerCase() == "e065" || v16elu.siteID.toLowerCase() == "e020" || v16elu.siteID.toLowerCase() == "e021")) {
            var q = (typeof(window.QueryString.htmlfid) !== "undefined") ? window.QueryString.htmlfid : "undefined";
            if (q == "undefined") {
                var r = String(v16elu.get_meta_tag("ContentInfo"));
                if (r !== "" && r.indexOf("~") !== -1) {
                    var j = r.split("~");
                    for (var s = 0; s < j.length; s++) {
                        if (j[s].split(":")[0] == "HTMLFID") {
                            q = j[s].split(":")[1]
                        }
                    }
                }
            }
            a = a + "-_-" + q
        }
        if (typeof(m.ibmConversion) == "undefined" && typeof(v16elu.siteID) !== "undefined" && v16elu.siteID.toLowerCase() == "hr") {
            var o = ["type", "wtm_c1", "wtm_c2", "wtm_c3", "wtm_c4", "wtm_c5", "lakey", "ns", "wc1", "wc2", "wc3", "wc4", "wc5", "e1", "e2", "e3", "ex1", "ex2", "opv", "osn", "fspv", "qt", "sortby", "pagenumber", "srt", "lcp", "lct"];
            for (var s = 0; s < o.length; s++) {
                if (typeof(m[o[s]]) == "undefined") {
                    m[o[s]] = "null"
                }
                a += "-_-" + m[o[s]]
            }
            a += "-_-" + document.title
        }
        if (m.ibmConversion && m.ibmConversion == "true") {
            if (!m.point && m.convtype && m.convtype == "1") {
                m.point = "10"
            }
            if (!m.point && m.convtype && m.convtype == "2") {
                m.point = "20"
            }
            var h = m.ibmEV + "-_-" + m.ibmEvAction + "-_-" + m.ibmEvName + "-_-" + m.ibmEvGroup + "-_-" + m.ibmEvModule + "-_-" + m.ibmEvSection + "-_-" + m.ibmEvTarget + "-_-" + m.ibmEvLinkTitle + "-_-" + m.ibmEvFileSize + "-_-" + m.ibmregoff + "-_-" + m.ibmregmail;
            if (typeof cmCreateConversionEventTag !== "undefined") {
                cmCreateConversionEventTag(e, m.convtype, w, m.point, h + "-_-" + a)
            }
        } else {
            if (m.ibmProductTag && m.ibmProductTag == "true") {
                window.onload = function () {
                    if (typeof(window.pageViewAttributes) != "undefined") {
                        var i = window.pageViewAttributes.split("-_-", 21).join("-_-")
                    }
                    if (typeof v16elu.siteID !== "undefined" && v16elu.siteID.toLowerCase() == "ecom" && typeof m.serviceType != "undefined") {
                        i += "-_--_--_--_--_--_--_--_--_--_-" + m.serviceType
                    }
                    if (typeof cmCreateProductviewTag !== "undefined") {
                        cmCreateProductviewTag(m.proID, m.proName, m.proCategory, i, m.cm_vc)
                    }
                }
            } else {
                if (typeof cmCreateElementTag !== "undefined") {
                    cmCreateElementTag(e, w, f + "-_--_--_-" + a)
                }
            }
        }
    },
    onPageLoad: function () {
        v16elu.event_tracking()
    },
    init: function () {
        var a = this;
        if (typeof(window.ibmweb) != "undefined" && typeof(window.ibmweb.config) != "undefined" && typeof(window.ibmweb.config.eluminate) != "undefined") {
            window.ibmweb.config.eluminate.enabled = true
        }
        this.create_QueryString();
        if (typeof(window.WebAnalytics) == "undefined") {
            window.WebAnalytics = {Page: {PageIdentifier: window.location.href}}
        }
        if (typeof(window.digitalData) == "undefined") {
            window.digitalData = {}
        }
        if (typeof v16elu.siteID === "undefined") {
            v16elu.siteID = String(v16elu.get_meta_tag("IBM.WTMSite"))
        }
        if (v16elu.siteID == "null") {
            v16elu.siteID = String(v16elu.get_meta_tag("WTMSite"))
        }
        if (v16elu.siteID == "null" && typeof digitalData.page !== "undefined") {
            if (typeof digitalData.page.site !== "undefined" && typeof digitalData.page.site.siteID !== "undefined") {
                v16elu.siteID = digitalData.page.site.siteID
            }
            if (v16elu.siteID == "null" && typeof digitalData.page.pageInfo !== "undefined" && typeof digitalData.page.pageInfo.ibm !== "undefined" && typeof digitalData.page.pageInfo.ibm.siteID !== "undefined") {
                v16elu.siteID = digitalData.page.pageInfo.ibm.siteID
            }
        }
        if (v16elu.siteID == "null") {
            v16elu.siteID = "IBMTESTW3"
        }
        v16elu.cmSetClientID.id = v16elu.CID + "|" + v16elu.siteID;
        if (typeof(window.cmTagQueue) == "undefined") {
            window.cmTagQueue = []
        }
        if (v16elu.siteID.substring(0, 4).toLowerCase() == "test" || v16elu.siteID.substring(v16elu.siteID.length - 4, v16elu.siteID.length).toLowerCase() == "test") {
            window.cmTagQueue.push(["cmSetClientID", "81040000|" + v16elu.siteID, false, "testdata.coremetrics.com", v16elu.cmSetClientID.cookieDomain])
        } else {
            window.cmTagQueue.push(["cmSetClientID", v16elu.cmSetClientID.id, v16elu.cmSetClientID.managedFirstParty, v16elu.cmSetClientID.dataCollectionDomain, v16elu.cmSetClientID.cookieDomain])
        }
        window.cmTagQueue.push(["cmSetupOther", {cm_JSFEAMasterIDSessionCookie: true}]);
        (function () {
            window.loadingTime = new Date().getTime();
            var b = document.createElement("script");
            b.setAttribute("type", "text/javascript");
            b.setAttribute("src", "//libs.coremetrics.com/eluminate.js");
            document.getElementsByTagName("head")[0].appendChild(b);
            if (v16elu.siteID.toLowerCase() == "p023") {
                v16elu.event_tracking()
            }
        })();
        if (v16elu.siteID.toLowerCase() !== "p023") {
            if (window.addEventListener) {
                window.addEventListener("load", v16elu.onPageLoad, false)
            } else {
                if (window.attachEvent) {
                    window.attachEvent("onload", v16elu.onPageLoad)
                }
            }
        }
    }
};
v16elu.Querystring.prototype.get = function (a, b) {
    var c = this.params[a];
    return (c != null) ? c : b
};
v16elu.Querystring.prototype.contains = function (a) {
    var b = this.params[a];
    return (b != null)
};
cmSetClientID = function () {
};
var ibmStats = ibmStats || {};
ibmStats.event = function (a) {
    v16elu.event_conversion(a)
};
if (typeof(window.ida_eluminate_enabled) !== "undefined") {
    if (!window.ida_eluminate_enabled) {
    } else {
        v16elu.init()
    }
} else {
    v16elu.init()
}
;