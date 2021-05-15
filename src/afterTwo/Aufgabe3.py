from plotnine import ggplot, geom_histogram, aes
import pandas as pd

df = pd.read_csv("PFAD ZUM OUTPUT")

p = (ggplot(df, aes(x="transcript_count"))
     + geom_histogram(bins=40))

print(p)
