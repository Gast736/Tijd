var mixedChart = new Chart(ctx, {
  type: 'bar',
  data: {
    datasets: [{
          label: 'Geregistreerde uren per week',
          data: [32, 40, 40, 38]
        }, {
          label: 'Contracturen per week',
          data: [40, 40, 40, 40],

          // Changes this dataset to become a line
          type: 'line'
        }],
    labels: ['week 1', 'week 2', 'week 51', 'week52']
  },
  options: options
});