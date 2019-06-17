import {Component, Inject, OnInit} from '@angular/core';
import {GuardsCheckEnd, NavigationStart, Router} from "@angular/router";
import {DOCUMENT} from "@angular/platform-browser";
import {AuthService} from "./services/auth/auth.service";
import {map} from "rxjs/operators";
import {Role} from "./models/Role";


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
})


export class AppComponent implements OnInit {
    url: string;
    KIT = ['kit', 'bootstrap_kit', 'jquery_js_kit', 'jquery-custom_js_kit', 'popper_js_kit', 'bootstrap_js_kit', 'bootstrap-switch_js_kit', 'nouislider_js_kit', 'moment_js_kit', 'datetimepicker_js_kit', 'core_js_kit'];
    DASH = ['dash', 'bootstrap_dash', 'jquery_js_dash', 'popper_js_dash', 'bootstrap_js_dash', 'scrollbar_js_dash', 'charts_js_dash', 'notify_js_dash', 'core_js_dash'];

    constructor(private router: Router, @Inject(DOCUMENT) private document: any, private authService: AuthService) {

    }

    ngOnInit() {

    }

    /*

        changeContainer(url: string) {
            if (this.url !== url) {
                url === '/admin' ? this.removeKitElements() : this.removeDashElements();
                this.url = url;
            }
        }

        removeKitElements() {
            let count = 0;
            this.KIT.forEach(id => {
                let e = this.document.getElementById(id);
                if (e !== null) {
                    e.remove();
                    count++;
                }
            });
            console.log("elmiinados de kit: " + count)
        }

        removeDashElements() {
            let count = 0;
            this.DASH.forEach(id => {
                let e = this.document.getElementById(id);
                if (e !== null) {
                    e.remove();
                    count++;

                }
            });
            console.log("elmiinados de dash: " + count)

        }
    */
}
