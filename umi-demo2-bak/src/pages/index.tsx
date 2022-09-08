import styles from './index.less';
import {history} from 'umi'

export default function IndexPage() {

  const goTodo = () => {
    //console.log('aaa');
    history.push("/todo");
  }

  return (
    <div>
      <h1 className={styles.title}>Page index</h1>
      <button onClick={goTodo}>Todo</button>
    </div>
  );
}
