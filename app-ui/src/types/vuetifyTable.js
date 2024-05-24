import {VDataTable, VDataTableServer, VDataTableVirtual,} from "vuetify/labs/VDataTable";
import {createVuetify} from "vuetify";

const vuetifyTable = createVuetify({
    components: {
        VDataTable,
        VDataTableServer,
        VDataTableVirtual,
    },
})
export default vuetifyTable;