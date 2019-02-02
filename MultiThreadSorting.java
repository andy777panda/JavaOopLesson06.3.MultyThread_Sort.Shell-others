package net.ukr.andy777;

public class MultiThreadSorting {

	// метод формування потоків, їх запуску (сортування частин) та отримання результату (складання відсортованих частин)
	static void sort(int[] array, int threadNumber, int sortMethod) {
		SingleThreadSorting[] threadarray = new SingleThreadSorting[threadNumber];
		// поділ масиву на частини + формування потоків з кожної частини та їх запуск (в конструкторі потоку)
		for (int i = 0; i < threadarray.length; i++) {
			int size = array.length / threadNumber;
			int begin = size * i;
			int end = ((i + 1) * size);
			if ((array.length - end) < size || i == (threadarray.length - 1)) {
				end = array.length;
			}
			threadarray[i] = new SingleThreadSorting(array, begin, end, sortMethod);
		}
		// приєднання всіх потоків для одночасного багатопотокового виконання
		for (int i = 0; i < threadarray.length; i++) {
			try {
				threadarray[i].getThr().join();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		// об'єднання відсортованих елементів масиву в завершених потоках в один масив
		System.arraycopy(mergeArrays(array, threadarray), 0, array, 0, array.length);
	}

	// метод об'єднання відсорованих елементів масиву в кожному потоці в єдиний відсортований масив
	private static int[] mergeArrays(int[] array, SingleThreadSorting[] threadarray) {
		int[] res = new int[array.length];
		for (int i = 0; i < res.length; i++) {
			int min = Integer.MAX_VALUE;
			int k = -1;
			for (int j = 0; j < threadarray.length; j++) {
				if (!threadarray[j].isStop() && min > threadarray[j].peekElement()) {
					// знаходження найменшого елементу з кожного потоку
					min = threadarray[j].peekElement();
					k = j;
				}
			}
			// формування результуючого масиву найменшим елементом з потоків
			if (k != -1) {
				res[i] = threadarray[k].pollElement();
			}
		}
		return res;
	}
}
