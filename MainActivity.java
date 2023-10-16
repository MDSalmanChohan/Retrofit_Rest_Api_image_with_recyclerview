public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        fetchData(); // Fetch data from your API
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://your-api-url.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<DataModel>> call = apiService.getData();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body());
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                // Handle network error
            }
        });
    }
}
