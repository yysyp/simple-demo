export default {
  'GET /mock/api/user': {
    status: 200,
    data: { id: 1, name: 'haha', age: 13 },
  },

  'GET /mock/api/product': {
    status: 200,
    data: {
      currentPage: 1,
      count: 11,
      products: [
        {
          id: 1,
          name: 'haha11',
          price: 11,
          desc: 'a1aa',
          createDate: new Date(),
        },
        {
          id: 2,
          name: 'hah2a',
          price: 22,
          desc: 'aaa2',
          createDate: new Date(),
        },
        {
          id: 3,
          name: 'haha3',
          price: 33,
          desc: 'aaa3',
          createDate: new Date(),
        },
        {
          id: 4,
          name: '4ss',
          price: 43,
          desc: 'aa4a',
          createDate: new Date(),
        },
        {
          id: 5,
          name: 'h5aha',
          price: 15,
          desc: '55gr',
          createDate: new Date(),
        },
        {
          id: 6,
          name: 'fee6',
          price: 63,
          desc: 'aaarf6',
          createDate: new Date(),
        },
        {
          id: 7,
          name: 'we7',
          price: 37,
          desc: 'rrw7',
          createDate: new Date(),
        },
        {
          id: 8,
          name: 'ee8',
          price: 3,
          desc: 'a8aa',
          createDate: new Date(),
        },
        {
          id: 9,
          name: 'hah9a',
          price: 55,
          desc: 'ghg59',
          createDate: new Date(),
        },
        {
          id: 10,
          name: 'dsw10',
          price: 10,
          desc: 'aa10a',
          createDate: new Date(),
        },
        {
          id: 11,
          name: 'aa11',
          price: 11,
          desc: 'qqe11',
          createDate: new Date(),
        },
      ],
    },
  },
};
