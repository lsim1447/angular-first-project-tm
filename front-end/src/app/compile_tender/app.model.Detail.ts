import { Task} from './../task/app.model.Task';
import { Tender} from './../tender/app.model.tender';

export class Detail{
    constructor(
        public detail_id: number,
        public tender: Tender,
        public task: Task,
        public quantity: number,
        public price: number
    ) {}
}