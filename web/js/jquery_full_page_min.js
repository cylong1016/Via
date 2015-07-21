/**
 * fullPage 2.2.5
 * https://github.com/alvarotrigo/fullPage.js
 * MIT licensed
 *
 * Copyright (C) 2013 alvarotrigo.com - A project by Alvaro Trigo
 */
(function(a) {
	a.fn.fullpage = function(c) {		
		function M() {
			a(".fp-section").each(function() {
				var b = a(this).find(".fp-slide");
				b.length ? b.each(function() {
					z(a(this))
				}) : z(a(this))
			});
			a.isFunction(c.afterRender) && c.afterRender.call(this)
		}
		function N() {
			if (!c.autoScrolling) {
				var b = a(window).scrollTop(),
				d = a(".fp-section").map(function() {
					if (a(this).offset().top < b + 100) return a(this)
				}),
				d = d[d.length - 1];
				if (!d.hasClass("active")) {
					var e = a(".fp-section.active").index(".fp-section") + 1;
					F = !0;
					var f = G(d);
					d.addClass("active").siblings().removeClass("active");
					var g = d.data("anchor");
					a.isFunction(c.onLeave) && c.onLeave.call(this, e, d.index(".fp-section") + 1, f);
					a.isFunction(c.afterLoad) && c.afterLoad.call(this, g, d.index(".fp-section") + 1);
					H(g);
					I(g, 0);
					c.anchors.length && !t && (v = g, location.hash = g);
					clearTimeout(O);
					O = setTimeout(function() {
						F = !1
					},
					100)
				}
			}
		}
		/*touch*/
		function da(b) {
			if(!h.hasClass("locked")){/*锁定判断*/
				var d = b.originalEvent;
				c.autoScrolling && b.preventDefault();
				if (!P(b.target) && (b = a(".fp-section.active"), !t && !p)) if (d = Q(d), w = d.y, A = d.x, b.find(".fp-slides").length && Math.abs(B - A) > Math.abs(x - w)) Math.abs(B - A) > a(window).width() / 100 * c.touchSensitivity && (B > A ? a.fn.fullpage.moveSlideRight() : a.fn.fullpage.moveSlideLeft());
				else if (c.autoScrolling && (d = b.find(".fp-slides").length ? b.find(".fp-slide.active").find(".fp-scrollable") : b.find(".fp-scrollable"), Math.abs(x - w) > a(window).height() / 100 * c.touchSensitivity)) if (x > w) if (0 < d.length) if (C("bottom", d)) a.fn.fullpage.moveSectionDown();
				else return ! 0;
				else a.fn.fullpage.moveSectionDown();
				else if (w > x) if (0 < d.length) if (C("top", d)) a.fn.fullpage.moveSectionUp();
				else return ! 0;
				else a.fn.fullpage.moveSectionUp()
			}
		}
		function P(b, d) {
			d = d || 0;
			var e = a(b).parent();
			return d < c.normalScrollElementTouchThreshold && e.is(c.normalScrollElements) ? !0 : d == c.normalScrollElementTouchThreshold ? !1 : P(e, ++d)
		}
		function ea(b) {
			b = Q(b.originalEvent);
			x = b.y;
			B = b.x
		}
		/*mouse*/
		function n(b) {
			if (c.autoScrolling && !h.hasClass("locked")) {/*锁定判断*/
				b = window.event || b;
				b = Math.max( - 1, Math.min(1, b.wheelDelta || -b.deltaY || -b.detail));
				var d;
				d = a(".fp-section.active");
				if (!t) if (d = d.find(".fp-slides").length ? d.find(".fp-slide.active").find(".fp-scrollable") : d.find(".fp-scrollable"), 0 > b) if (0 < d.length) if (C("bottom", d)) a.fn.fullpage.moveSectionDown();
				else return ! 0;
				else a.fn.fullpage.moveSectionDown();
				else if (0 < d.length) if (C("top", d)) a.fn.fullpage.moveSectionUp();
				else return ! 0;
				else a.fn.fullpage.moveSectionUp();
				return ! 1
			}
		}
		function R(b) {
			var d = a(".fp-section.active").find(".fp-slides");
			if (d.length && !p) {
				var e = d.find(".fp-slide.active"),
				f = null,
				f = "prev" === b ? e.prev(".fp-slide") : e.next(".fp-slide");
				if (!f.length) {
					if (!c.loopHorizontal) return;
					f = "prev" === b ? e.siblings(":last") : e.siblings(":first")
				}
				p = !0;
				q(d, f)
			}
		}
		function k(b, d, e) {
			var f = {},
			g = b.position();
			if ("undefined" !== typeof g) {
				var g = g.top,
				y = G(b),
				r = b.data("anchor"),
				h = b.index(".fp-section"),
				p = b.find(".fp-slide.active"),
				s = a(".fp-section.active"),
				l = s.index(".fp-section") + 1,
				E = D;
				if (p.length) var n = p.data("anchor"),
				q = p.index();
				if (c.autoScrolling && c.continuousVertical && "undefined" !== typeof e && (!e && "up" == y || e && "down" == y)) {
					e ? a(".fp-section.active").before(s.nextAll(".fp-section")) : a(".fp-section.active").after(s.prevAll(".fp-section").get().reverse());
					u(a(".fp-section.active").position().top);
					var k = s,
					g = b.position(),
					g = g.top,
					y = G(b)
				}
				b.addClass("active").siblings().removeClass("active");
				t = !0;
				"undefined" !== typeof r && S(q, n, r);
				c.autoScrolling ? (f.top = -g, b = ".fullpage-wrapper") : (f.scrollTop = g, b = "html, body");
				var m = function() {
					k && k.length && (e ? a(".fp-section:first").before(k) : a(".fp-section:last").after(k), u(a(".fp-section.active").position().top))
				};
				c.css3 && c.autoScrolling ? (a.isFunction(c.onLeave) && !E && c.onLeave.call(this, l, h + 1, y), T("translate3d(0px, -" + g + "px, 0px)", !0), setTimeout(function() {
					m();
					a.isFunction(c.afterLoad) && !E && c.afterLoad.call(this, r, h + 1);
					setTimeout(function() {
						t = !1;
						a.isFunction(d) && d.call(this)
					},
					600)
				},
				c.scrollingSpeed)) : (a.isFunction(c.onLeave) && !E && c.onLeave.call(this, l, h + 1, y), a(b).animate(f, c.scrollingSpeed, c.easing,
				function() {
					m();
					a.isFunction(c.afterLoad) && !E && c.afterLoad.call(this, r, h + 1);
					setTimeout(function() {
						t = !1;
						a.isFunction(d) && d.call(this)
					},
					600)
				}));
				v = r;
				c.autoScrolling && (H(r), I(r, h))
			}
		}
		function U() {
			if (!F) {
				var b = window.location.hash.replace("#", "").split("/"),
				a = b[0],
				b = b[1];
				if (a.length) {
					var c = "undefined" === typeof v,
					f = "undefined" === typeof v && "undefined" === typeof b && !p; (a && a !== v && !c || f || !p && J != b) && K(a, b)
				}
			}
		}
		function q(b, d) {
			var e = d.position(),
			f = b.find(".fp-slidesContainer").parent(),
			g = d.index(),
			h = b.closest(".fp-section"),
			r = h.index(".fp-section"),
			k = h.data("anchor"),
			l = h.find(".fp-slidesNav"),
			s = d.data("anchor"),
			m = D;
			if (c.onSlideLeave) {
				var n = h.find(".fp-slide.active").index(),
				q;
				q = n == g ? "none": n > g ? "left": "right";
				m || a.isFunction(c.onSlideLeave) && c.onSlideLeave.call(this, k, r + 1, n, q)
			}
			d.addClass("active").siblings().removeClass("active");
			"undefined" === typeof s && (s = g);
			c.loopHorizontal || (h.find(".fp-controlArrow.fp-prev").toggle(0 != g), h.find(".fp-controlArrow.fp-next").toggle(!d.is(":last-child")));
			h.hasClass("active") && S(g, s, k);
			c.css3 ? (e = "translate3d(-" + e.left + "px, 0px, 0px)", b.find(".fp-slidesContainer").toggleClass("fp-easing", 0 < c.scrollingSpeed).css(V(e)), setTimeout(function() {
				m || a.isFunction(c.afterSlideLoad) && c.afterSlideLoad.call(this, k, r + 1, s, g);
				p = !1
			},
			c.scrollingSpeed, c.easing)) : f.animate({
				scrollLeft: e.left
			},
			c.scrollingSpeed, c.easing,
			function() {
				m || a.isFunction(c.afterSlideLoad) && c.afterSlideLoad.call(this, k, r + 1, s, g);
				p = !1
			});
			l.find(".active").removeClass("active");
			l.find("li").eq(g).find("a").addClass("active")
		}
		function fa(b, d) {
			if (825 > b || 900 > d) {
				var c = Math.min(100 * b / 825, 100 * d / 900).toFixed(2);
				a("body").css("font-size", c + "%")
			} else a("body").css("font-size", "100%")
		}
		function I(b, d) {
			c.navigation && (a("#fp-nav").find(".active").removeClass("active"), b ? a("#fp-nav").find('a[href="#' + b + '"]').addClass("active") : a("#fp-nav").find("li").eq(d).find("a").addClass("active"))
		}
		function H(b) {
			c.menu && (a(c.menu).find(".active").removeClass("active"), a(c.menu).find('[data-menuanchor="' + b + '"]').addClass("active"))
		}
		function C(b, a) {
			if ("top" === b) return ! a.scrollTop();
			if ("bottom" === b) return a.scrollTop() + 1 + a.innerHeight() >= a[0].scrollHeight
		}
		function G(b) {
			var c = a(".fp-section.active").index(".fp-section");
			b = b.index(".fp-section");
			return c > b ? "up": "down"
		}
		function z(b) {
			b.css("overflow", "hidden");
			var a = b.closest(".fp-section"),
			e = b.find(".fp-scrollable");
			if (e.length) var f = e.get(0).scrollHeight;
			else f = b.get(0).scrollHeight,
			c.verticalCentered && (f = b.find(".fp-tableCell").get(0).scrollHeight);
			a = l - parseInt(a.css("padding-bottom")) - parseInt(a.css("padding-top"));
			f > a ? e.length ? e.css("height", a + "px").parent().css("height", a + "px") : (c.verticalCentered ? b.find(".fp-tableCell").wrapInner('<div class="fp-scrollable" />') : b.wrapInner('<div class="fp-scrollable" />'), b.find(".fp-scrollable").slimScroll({
				allowPageScroll: !0,
				height: a + "px",
				size: "10px",
				alwaysVisible: !0
			})) : W(b);
			b.css("overflow", "")
		}
		function W(a) {
			a.find(".fp-scrollable").children().first().unwrap().unwrap();
			a.find(".slimScrollBar").remove();
			a.find(".slimScrollRail").remove()
		}
		function X(a) {
			a.addClass("fp-table").wrapInner('<div class="fp-tableCell" style="height:' + Y(a) + 'px;" />')
		}
		function Y(a) {
			var d = l;
			if (c.paddingTop || c.paddingBottom) d = a,
			d.hasClass("fp-section") || (d = a.closest(".fp-section")),
			a = parseInt(d.css("padding-top")) + parseInt(d.css("padding-bottom")),
			d = l - a;
			return d
		}
		function T(a, c) {
			h.toggleClass("fp-easing", c);
			h.css(V(a))
		}
		function K(b, c) {
			"undefined" === typeof c && (c = 0);
			var e = isNaN(b) ? a('[data-anchor="' + b + '"]') : a(".fp-section").eq(b - 1);
			b === v || e.hasClass("active") ? Z(e, c) : k(e,
			function() {
				Z(e, c)
			})
		}
		function Z(a, c) {
			if ("undefined" != typeof c) {
				var e = a.find(".fp-slides"),
				f = e.find('[data-anchor="' + c + '"]');
				f.length || (f = e.find(".fp-slide").eq(c));
				f.length && q(e, f)
			}
		}
		function ga(a, d) {
			a.append('<div class="fp-slidesNav"><ul></ul></div>');
			var e = a.find(".fp-slidesNav");
			e.addClass(c.slidesNavPosition);
			for (var f = 0; f < d; f++) e.find("ul").append('<li><a href="#"><span></span></a></li>');
			e.css("margin-left", "-" + e.width() / 2 + "px");
			e.find("li").first().find("a").addClass("active")
		}
		function S(a, d, e) {
			var f = "";
			c.anchors.length && (a ? ("undefined" !== typeof e && (f = e), "undefined" === typeof d && (d = a), J = d, location.hash = f + "/" + d) : ("undefined" !== typeof a && (J = d), location.hash = e))
		}
		function ha() {
			var a = document.createElement("p"),
			c,
			e = {
				webkitTransform: "-webkit-transform",
				OTransform: "-o-transform",
				msTransform: "-ms-transform",
				MozTransform: "-moz-transform",
				transform: "transform"
			};
			document.body.insertBefore(a, null);
			for (var f in e) void 0 !== a.style[f] && (a.style[f] = "translate3d(1px,1px,1px)", c = window.getComputedStyle(a).getPropertyValue(e[f]));
			document.body.removeChild(a);
			return void 0 !== c && 0 < c.length && "none" !== c
		}
		function $() {
			return window.PointerEvent ? {
				down: "pointerdown",
				move: "pointermove"
			}: {
				down: "MSPointerDown",
				move: "MSPointerMove"
			}
		}
		function Q(a) {
			var c = [];
			window.navigator.msPointerEnabled ? (c.y = a.pageY, c.x = a.pageX) : (c.y = a.touches[0].pageY, c.x = a.touches[0].pageX);
			return c
		}
		function aa(b) {
			var d = c.scrollingSpeed;
			a.fn.fullpage.setScrollingSpeed(0);
			q(b.closest(".fp-slides"), b);
			a.fn.fullpage.setScrollingSpeed(d)
		}
		function u(a) {
			c.css3 ? T("translate3d(0px, -" + a + "px, 0px)", !1) : h.css("top", -a)
		}
		function V(a) {
			return {
				"-webkit-transform": a,
				"-moz-transform": a,
				"-ms-transform": a,
				transform: a
			}
		}
		function ia() {
			u(0);
			a("#fp-nav, .fp-slidesNav, .fp-controlArrow").remove();
			a(".fp-section").css({
				height: "",
				"background-color": "",
				padding: ""
			});
			a(".fp-slide").css({
				width: ""
			});
			h.css({
				height: "",
				position: "",
				"-ms-touch-action": ""
			});
			a(".fp-section, .fp-slide").each(function() {
				W(a(this));
				a(this).removeClass("fp-table active")
			});
			h.find(".fp-easing").removeClass("fp-easing");
			h.find(".fp-tableCell, .fp-slidesContainer, .fp-slides").each(function() {
				a(this).replaceWith(this.childNodes)
			});
			a("html, body").scrollTop(0);
			h.addClass("fullpage-used")
		}
		c = a.extend({
			verticalCentered: !0,
			resize: !0,
			sectionsColor: [],
			anchors: [],
			scrollingSpeed: 700,
			easing: "easeInQuart",
			menu: !1,
			navigation: !1,
			navigationPosition: "right",
			navigationColor: "#000",
			navigationTooltips: [],
			slidesNavigation: !1,
			slidesNavPosition: "bottom",
			controlArrowColor: "#fff",
			loopBottom: !1,
			loopTop: !1,
			loopHorizontal: !0,
			autoScrolling: !0,
			scrollOverflow: !1,
			css3: !1,
			paddingTop: 0,
			paddingBottom: 0,
			fixedElements: null,
			normalScrollElements: null,
			keyboardScrolling: !0,
			touchSensitivity: 5,
			continuousVertical: !1,
			animateAnchor: !0,
			normalScrollElementTouchThreshold: 5,
			sectionSelector: ".section",
			slideSelector: ".slide",
			afterLoad: null,
			onLeave: null,
			afterRender: null,
			afterResize: null,
			afterSlideLoad: null,
			onSlideLeave: null
		},
		c);
		c.continuousVertical && (c.loopTop || c.loopBottom) && (c.continuousVertical = !1, console && console.log && console.log("Option loopTop/loopBottom is mutually exclusive with continuousVertical; continuousVertical disabled"));
		a.fn.fullpage.setAutoScrolling = function(b) {
			c.autoScrolling = b;
			b = a(".fp-section.active");
			c.autoScrolling ? (a("html, body").css({
				overflow: "hidden",
				height: "100%"
			}), b.length && u(b.position().top)) : (a("html, body").css({
				overflow: "visible",
				height: "initial"
			}), u(0), a("html, body").scrollTop(b.position().top))
		};
		a.fn.fullpage.setScrollingSpeed = function(a) {
			c.scrollingSpeed = a
		};
		a.fn.fullpage.setMouseWheelScrolling = function(a) {
			a ? document.addEventListener ? (document.addEventListener("mousewheel", n, !1), document.addEventListener("wheel", n, !1)) : document.attachEvent("onmousewheel", n) : document.addEventListener ? (document.removeEventListener("mousewheel", n, !1), document.removeEventListener("wheel", n, !1)) : document.detachEvent("onmousewheel", n)
		};
		a.fn.fullpage.setAllowScrolling = function(b) {
			if (b) {
				if (a.fn.fullpage.setMouseWheelScrolling(!0), L || ba) MSPointer = $(),
				a(document).off("touchstart " + MSPointer.down).on("touchstart " + MSPointer.down, ea),
				a(document).off("touchmove " + MSPointer.move).on("touchmove " + MSPointer.move, da)
			} else if (a.fn.fullpage.setMouseWheelScrolling(!1), L || ba) MSPointer = $(),
			a(document).off("touchstart " + MSPointer.down),
			a(document).off("touchmove " + MSPointer.move)
		};
		a.fn.fullpage.setKeyboardScrolling = function(a) {
			c.keyboardScrolling = a
		};
		var p = !1,
		L = navigator.userAgent.match(/(iPhone|iPod|iPad|Android|BlackBerry|BB10|Windows Phone|Tizen|Bada)/),
		ba = "ontouchstart" in window || 0 < navigator.msMaxTouchPoints,
		h = a(this),
		l = a(window).height(),
		t = !1,
		D = !1,
		v,
		J;
		a.fn.fullpage.setAllowScrolling(!0);
		c.css3 && (c.css3 = ha());
		a(this).length ? (h.css({
			height: "100%",
			position: "relative",
			"-ms-touch-action": "none"
		}), h.addClass("fullpage-wrapper")) : console.error("Error! Fullpage.js needs to be initialized with a selector. For example: $('#myContainer').fullpage();");
		a(c.sectionSelector).each(function() {
			a(this).addClass("fp-section")
		});
		a(c.slideSelector).each(function() {
			a(this).addClass("fp-slide")
		});
		if (c.navigation) {
			a("body").append('<div id="fp-nav"><ul></ul></div>');
			var m = a("#fp-nav");
			m.css("color", c.navigationColor);
			m.addClass(c.navigationPosition)
		}
		a(".fp-section").each(function(b) {
			var d = a(this),
			e = a(this).find(".fp-slide"),
			f = e.length;
			b || 0 !== a(".fp-section.active").length || a(this).addClass("active");
			a(this).css("height", l + "px"); (c.paddingTop || c.paddingBottom) && a(this).css("padding", c.paddingTop + " 0 " + c.paddingBottom + " 0");
			"undefined" !== typeof c.sectionsColor[b] && a(this).css("background-color", c.sectionsColor[b]);
			"undefined" !== typeof c.anchors[b] && a(this).attr("data-anchor", c.anchors[b]);
			if (c.navigation) {
				var g = "";
				c.anchors.length && (g = c.anchors[b]);
				b = c.navigationTooltips[b];
				"undefined" === typeof b && (b = "");
				m.find("ul").append('<li data-tooltip="' + b + '"><a href="#' + g + '"><span></span></a></li>')
			}
			if (1 < f) {
				var g = 100 * f,
				h = 100 / f;
				e.wrapAll('<div class="fp-slidesContainer" />');
				e.parent().wrap('<div class="fp-slides" />');
				a(this).find(".fp-slidesContainer").css("width", g + "%");
				a(this).find(".fp-slides").after('<div class="fp-controlArrow fp-prev"></div><div class="fp-controlArrow fp-next"></div>');
				"#fff" != c.controlArrowColor && (a(this).find(".fp-controlArrow.fp-next").css("border-color", "transparent transparent transparent " + c.controlArrowColor), a(this).find(".fp-controlArrow.fp-prev").css("border-color", "transparent " + c.controlArrowColor + " transparent transparent"));
				c.loopHorizontal || a(this).find(".fp-controlArrow.fp-prev").hide();
				c.slidesNavigation && ga(a(this), f);
				e.each(function(b) {
					//a(this).css("width", h + "%");
					a(this).css("width", a(window).width());
					c.verticalCentered && X(a(this))
				});
				d = d.find(".fp-slide.active");
				0 == d.length ? e.eq(0).addClass("active") : aa(d)
			} else c.verticalCentered && X(a(this))
		}).promise().done(function() {
			a.fn.fullpage.setAutoScrolling(c.autoScrolling);
			var b = a(".fp-section.active").find(".fp-slide.active");
			b.length && (0 != a(".fp-section.active").index(".fp-section") || 0 == a(".fp-section.active").index(".fp-section") && 0 != b.index()) && aa(b);
			c.fixedElements && c.css3 && a(c.fixedElements).appendTo("body");
			c.navigation && (m.css("margin-top", "-" + m.height() / 2 + "px"), m.find("li").eq(a(".fp-section.active").index(".fp-section")).find("a").addClass("active"));
			c.menu && c.css3 && a(c.menu).closest(".fullpage-wrapper").length && a(c.menu).appendTo("body");
			c.scrollOverflow ? (h.hasClass("fullpage-used") && M(), a(window).on("load", M)) : a.isFunction(c.afterRender) && c.afterRender.call(this);
			b = window.location.hash.replace("#", "").split("/")[0];
			if (b.length) {
				var d = a('[data-anchor="' + b + '"]'); ! c.animateAnchor && d.length && (c.autoScrolling ? u(d.position().top) : (u(0), a("html, body").scrollTop(d.position().top)), H(b), I(b, null), a.isFunction(c.afterLoad) && c.afterLoad.call(this, b, d.index(".fp-section") + 1), d.addClass("active").siblings().removeClass("active"))
			}
			a(window).on("load",
			function() {
				var a = window.location.hash.replace("#", "").split("/"),
				b = a[0],
				a = a[1];
				b && K(b, a)
			})
		});
		var O, F = !1;
		a(window).on("scroll", N);
		var x = 0,
		B = 0,
		w = 0,
		A = 0;
		a.fn.fullpage.moveSectionUp = function() {
			var b = a(".fp-section.active").prev(".fp-section");
			b.length || !c.loopTop && !c.continuousVertical || (b = a(".fp-section").last());
			b.length && k(b, null, !0)
		};
		a.fn.fullpage.moveSectionDown = function() {
			var b = a(".fp-section.active").next(".fp-section");
			b.length || !c.loopBottom && !c.continuousVertical || (b = a(".fp-section").first());
			b.length && k(b, null, !1)
			addSel();
		};
		a.fn.fullpage.moveTo = function(b, c) {
			var e = "",
			e = isNaN(b) ? a('[data-anchor="' + b + '"]') : a(".fp-section").eq(b - 1);
			"undefined" !== typeof c ? K(b, c) : 0 < e.length && k(e)
		};
		a.fn.fullpage.moveSlideRight = function() {
			R("next")
		};
		a.fn.fullpage.moveSlideLeft = function() {
			R("prev")
		};
		a(window).on("hashchange", U);
		a(document).keydown(function(b) {
			if (c.keyboardScrolling && !t) switch (b.which) {
			case 38:
			case 33:
				a.fn.fullpage.moveSectionUp();
				break;
			case 40:
			case 34:
				a.fn.fullpage.moveSectionDown();
				break;
			case 36:
				a.fn.fullpage.moveTo(1);
				break;
			case 35:
				a.fn.fullpage.moveTo(a(".fp-section").length);
				break;
			case 37:
				a.fn.fullpage.moveSlideLeft();
				break;
			case 39:
				a.fn.fullpage.moveSlideRight()
			}
		});
		a(document).on("click", "#fp-nav a",
		function(b) {
			b.preventDefault();
			b = a(this).parent().index();
			k(a(".fp-section").eq(b))
		});
		a(document).on({
			mouseenter: function() {
				var b = a(this).data("tooltip");
				a('<div class="fp-tooltip ' + c.navigationPosition + '">' + b + "</div>").hide().appendTo(a(this)).fadeIn(200)
			},
			mouseleave: function() {
				a(this).find(".fp-tooltip").fadeOut(200,
				function() {
					a(this).remove()
				})
			}
		},
		"#fp-nav li");
		c.normalScrollElements && (a(document).on("mouseenter", c.normalScrollElements,
		function() {
			a.fn.fullpage.setMouseWheelScrolling(!1)
		}), a(document).on("mouseleave", c.normalScrollElements,
		function() {
			a.fn.fullpage.setMouseWheelScrolling(!0)
		}));
		a(".fp-section").on("click", ".fp-controlArrow",
		function() {
			a(this).hasClass("fp-prev") ? a.fn.fullpage.moveSlideLeft() : a.fn.fullpage.moveSlideRight()
		});
		a(".fp-section").on("click", ".toSlide",
		function(b) {
			b.preventDefault();
			b = a(this).closest(".fp-section").find(".fp-slides");
			b.find(".fp-slide.active");
			var c = null,
			c = b.find(".fp-slide").eq(a(this).data("index") - 1);
			0 < c.length && q(b, c)
		});
		var ca;
		a(window).resize(function() {
			L ? a.fn.fullpage.reBuild() : (clearTimeout(ca), ca = setTimeout(a.fn.fullpage.reBuild, 500))
		});
		a.fn.fullpage.reBuild = function() {
			D = !0;
			var b = a(window).width();
			l = a(window).height();
			c.resize && fa(l, b);
			a(".fp-section").each(function() {
				parseInt(a(this).css("padding-bottom"));
				parseInt(a(this).css("padding-top"));
				c.verticalCentered && a(this).find(".fp-tableCell").css("height", Y(a(this)) + "px");
				a(this).css("height", l + "px");
				if (c.scrollOverflow) {
					var b = a(this).find(".fp-slide");
					b.length ? b.each(function() {
						z(a(this))
					}) : z(a(this))
				}
				b = a(this).find(".fp-slides");
				b.length && q(b, b.find(".fp-slide.active"))
			});
			a(".fp-section.active").position();
			b = a(".fp-section.active");
			b.index(".fp-section") && k(b);
			D = !1;
			a.isFunction(c.afterResize) && c.afterResize.call(this)
		};
		a(document).on("click", ".fp-slidesNav a",
		function(b) {
			b.preventDefault();
			b = a(this).closest(".fp-section").find(".fp-slides");
			var c = b.find(".fp-slide").eq(a(this).closest("li").index());
			q(b, c)
		});
		a.fn.fullpage.destroy = function(b) {
			a.fn.fullpage.setAutoScrolling(!1);
			a.fn.fullpage.setAllowScrolling(!1);
			a.fn.fullpage.setKeyboardScrolling(!1);
			a(window).off("scroll", N).off("hashchange", U);
			a(document).off("click", "#fp-nav a").off("mouseenter", "#fp-nav li").off("mouseleave", "#fp-nav li").off("click", ".fp-slidesNav a").off("mouseover", c.normalScrollElements).off("mouseout", c.normalScrollElements);
			a(".fp-section").off("click", ".fp-controlArrow").off("click", ".toSlide");
			b && ia()
		}
	}
})(jQuery);