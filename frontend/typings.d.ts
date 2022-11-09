declare module '*.css';
declare module '*.less';
declare module '*.png';
declare module '*.svg' {
  export function ReactComponent(
    props: React.SVGProps<SVGSVGElement>,
  ): React.ReactElement;
  const url: string;
  export default url;
}

declare type ObjectType = Record<string, any>;
declare type BooleanType = 'Y' | 'N';

declare const ENV: string;
declare const reqBaseUrl: string;

declare interface CommonRes {
  status: string;
}