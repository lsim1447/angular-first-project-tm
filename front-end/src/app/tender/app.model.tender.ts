export class Tender{
    constructor(
        public tender_id: number,
        public own_name: string,
        public recipient_name: string,
        public choosed_date: string,
        public total: number,
        public description: string
    ){};
}