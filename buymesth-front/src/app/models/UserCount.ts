export class UserCount {

    enabled: number;
    disabled: number;
    locked: number;

    constructor(enabled: number, disabled: number, locked: number) {
        this.enabled = enabled;
        this.disabled = disabled;
        this.locked = locked;
    }
}